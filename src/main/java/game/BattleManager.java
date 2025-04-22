/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Muhammad Ahmed
 * Date: 4/14/2025
 * Time: 2:43 PM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: Player
 *
 * Description:
 *
 * ****************************************
 */

package game;

import cards.*;
import creature.Creature;

import java.util.List;
import java.util.Scanner;

/**
 * Manages battle state between a player and an enemy creature.
 * Tracks summoned creatures and determines if battle is won.
 * @author Ahmed
 */
public class BattleManager {
    private final Player player;
    private final Creature enemyCreature;
    private boolean victoryStatus;
    private final List<Creature> summonedCreatures;

    /**
     * Constructor for the battle manager.
     * @param player the Player involved in the battle
     * @param enemyCreature the enemy Creature
     */
    public BattleManager(Player player, Creature enemyCreature) {
        this.player = player;
        this.enemyCreature = enemyCreature;
        this.victoryStatus = false;
        this.summonedCreatures = player.getSummonedCreatures();
    }

    /**
     * Main loop that is repeated to simulate the battle
     *
     * @author Nathan Ramkissoon
     */
    public void battleLoop() {
        startBattle();
        for (int i = 0; i < player.getMaxCardsInHand(); i++) {
            player.drawCard();
        }
        while (!victoryStatus) {
            playerTurn();
            if (checkVictory()) {
                break;
            }
            summonedCreaturesTurn();
            if (checkVictory()) {
                break;
            }
            enemyTurn();
            if (checkDefeat()) {
                System.out.println("You lose!");
                break;
            }
            removeDefeatedCreatures();
        }
    }

    /**
     * Starts the battle — could later be expanded into turn-based logic.
     */
    private void startBattle() {
        victoryStatus = false;
        System.out.println("Battle started between player and enemy: " + enemyCreature.getName());
    }

    /**
     * Player chooses a card and plays it, which may cause damage to the enemy
     */
    private void playerTurn() {
        System.out.println("Choose your card from indices 0 - 3");
        for (Card cardInHand : player.getCurrentHand()) {
            System.out.println(cardInHand.getName() + ", " + cardInHand.getType());
        }
        Scanner scanner = new Scanner(System.in);
        int handIndex = scanner.nextInt();
        Card chosenCard = player.getCurrentHand().get(handIndex);
        int damageToEnemy = useCard(chosenCard);
        enemyCreature.takeDamage(damageToEnemy);
        System.out.println(enemyCreature.getName() + " took " + damageToEnemy);
        scanner.close();
    }

    /**
     * Uses a card that is selected by the player by applying its effect or finding its damage, then discarding it
     *
     * @param usedCard Card that the player has selected
     * @return integer of the number of damage the selected card deals
     *
     * @author Nathan Ramkissoon
     */
    private int useCard(Card usedCard) {
        // Initialize to 0, because status and creature cards do no damage
        int damageNumber = 0;
        // Checks what type the card is, and calls the correct method
        switch (usedCard.getType()) {
            case CardType.STATUS:
                StatusCard usedStatusCard = (StatusCard) usedCard;
                // If the card decreases damage, they are applied to the enemy
                // If it buffs or heals, the effect is applied to allied summoned creatures
                if (usedStatusCard.getCardEffect().getEffect().equalsIgnoreCase("decrease")) {
                    usedStatusCard.useStatusCard(enemyCreature);
                } else {
                    for (Creature summonedCreature : summonedCreatures) {
                        usedStatusCard.useStatusCard(summonedCreature);
                    }
                }
                break;
            case CardType.ATTACK:
                AttackCard usedAttackCard = (AttackCard) usedCard;
                // The attack card's damage is collected
                damageNumber = usedAttackCard.useAttackCard();
                break;
            case CardType.CREATURE:
                CreatureCard usedCreatureCard = (CreatureCard) usedCard;
                Creature creature = usedCreatureCard.useCreatureCard();
                summonedCreatures.add(creature);
                break;
            default:
                System.out.println("Card has invalid type");
                break;
        }
        // Discard the card from the player's hand
        player.discardCard(usedCard);
        player.drawCard();
        return damageNumber;
    }

    /**
     * Checks if the enemy creature has been defeated.
     */
    private boolean checkVictory() {
        if (!enemyCreature.isAlive()) {
            victoryStatus = true;
            System.out.println("You won!");
        }
        return victoryStatus;
    }

    /**
     * All summoned creatures do damage to the enemy
     *
     * @author Nathan Ramkissoon
     */
    private void summonedCreaturesTurn() {
        if (!summonedCreatures.isEmpty()) {
            for (Creature summonedCreature : summonedCreatures) {
                enemyCreature.takeDamage(summonedCreature.getDamage());
            }
        }
    }

    /**
     * Enemy does damage to the player and all summoned creatures
     *
     * @author Nathan Ramkissoon
     */
    private void enemyTurn() {
        player.takeDamage(enemyCreature.getDamage());
        System.out.println("Player took " + enemyCreature.getDamage() + " damage");
        if (!summonedCreatures.isEmpty()) {
            for (Creature summonedCreature : summonedCreatures) {
                summonedCreature.takeDamage(enemyCreature.getDamage());
                System.out.println(summonedCreature.getName() + " took " + enemyCreature.getDamage() + " damage");
            }
        }
    }

    public boolean checkDefeat() {
        return player.getIfAlive();
    }

    private void removeDefeatedCreatures() {
        if (!summonedCreatures.isEmpty()) {
            for (Creature summonedCreature : summonedCreatures) {
                if (!summonedCreature.isAlive()) {
                    summonedCreatures.remove(summonedCreature);
                    System.out.println(summonedCreature.getName() + " was defeated...");
                }
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Creature getEnemyCreature() {
        return enemyCreature;
    }

    public List<Creature> getSummonedCreatures() {
        return summonedCreatures;
    }

    public boolean isVictory() {
        return victoryStatus;
    }
}
