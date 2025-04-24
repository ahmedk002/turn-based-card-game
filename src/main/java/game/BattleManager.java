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
 * Description: Class that organizes a battle between the player and an enemy creature
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
     *
     * @author Muhammad Ahmed
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
        while (!isVictory()) {
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
                break;
            }
            removeDefeatedCreatures();
        }
    }

    /**
     * Starts the battle — could later be expanded into turn-based logic.
     *
     * @author Muhammad Ahmed
     */
    private void startBattle() {
        victoryStatus = false;
        System.out.println("Battle started between player and enemy: " + enemyCreature.getName());
    }

    /**
     * Player chooses a card and plays it, which may cause damage to the enemy
     *
     * @author Nathan Ramkissoon
     */
    private void playerTurn() {
        // Gets card from player's input; used for temporary terminal functionality
        Scanner scanner = new Scanner(System.in);
        int handIndex = scanner.nextInt();
        Card chosenCard = player.getCurrentHand().get(handIndex);
        int damageToEnemy = useCard(chosenCard);
        enemyCreature.takeDamage(damageToEnemy);
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
        // Initialized to 0, as status and creature cards do no damage
        int damageNumber = 0;
        // Checks what type the card is, and calls the correct method
        switch (usedCard.getType()) {
            case CardType.STATUS:
                StatusCard usedStatusCard = (StatusCard) usedCard;
                // If the card decreases damage, the effect is applied to the enemy
                // If it buffs damage or heals, the effect is instead applied to allied summoned creatures
                if (usedStatusCard.getCardEffect().getEffect().toLowerCase().contains("decrease")) {
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
                // Gets a creature from the card's information and adds it to the summoned creature list
                Creature creature = usedCreatureCard.useCreatureCard();
                summonedCreatures.add(creature);
                break;
            default:
                throw new IllegalArgumentException("Card has invalid type");
        }
        // Discard the card from the player's hand
        player.discardCard(usedCard);
        // Draw a new card if able to
        player.drawCard();
        // Return the amount of damage dealt by the card
        return damageNumber;
    }

    /**
     * Checks if the enemy creature has been defeated.
     *
     * @author Muhammad Ahmed
     */
    private boolean checkVictory() {
        if (!enemyCreature.isAlive()) {
            victoryStatus = true;
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
        if (!summonedCreatures.isEmpty()) {
            for (Creature summonedCreature : summonedCreatures) {
                summonedCreature.takeDamage(enemyCreature.getDamage());
            }
        }
    }

    /**
     * Checks if the player is dead or their current hand is empty, meaning they ran out of cards
     *
     * @return boolean that shows if the player is defeated
     */
    public boolean checkDefeat() {
        return !player.getIfAlive() || player.getCurrentHand().isEmpty();
    }

    /**
     * Removes creatures from the summoned creatures list if they are defeated
     *
     * @author Nathan Ramkissoon
     */
    private void removeDefeatedCreatures() {
        if (!summonedCreatures.isEmpty()) {
            for (Creature summonedCreature : summonedCreatures) {
                if (!summonedCreature.isAlive()) {
                    summonedCreatures.remove(summonedCreature);
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
