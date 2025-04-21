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

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages battle state between a player and an enemy creature.
 * Tracks summoned creatures and determines if battle is won.
 * @author Ahmed
 */
public class BattleManager {
    private Player player;
    private Creature enemyCreature;
    private boolean victoryStatus;
    private List<Creature> summonedCreatures;

    /**
     * Constructor for the battle manager.
     * @param player the Player involved in the battle
     * @param enemyCreature the enemy Creature
     */
    public BattleManager(Player player, Creature enemyCreature) {
        this.player = player;
        this.enemyCreature = enemyCreature;
        this.victoryStatus = false;
        this.summonedCreatures = new ArrayList<>();
    }

    /**
     * Starts the battle — could later be expanded into turn-based logic.
     */
    public void startBattle() {
        System.out.println("Battle started between player and enemy: " + enemyCreature.getName());
    }

    /**
     * Checks if the enemy creature has been defeated.
     * @return true if enemy is dead, false otherwise
     */
    public boolean checkVictory() {
        if (!enemyCreature.isAlive()) {
            victoryStatus = true;
        }
        return victoryStatus;
    }

    /**
     * Updates battle state — currently only checks if enemy is dead.
     */
    public void updateBattleState() {
        // You can add logic to simulate one round, or update turn state
        System.out.println("Updating battle state...");
        if (!enemyCreature.isAlive()) {
            System.out.println("Enemy defeated!");
            victoryStatus = true;
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

    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        int handIndex = scanner.nextInt();
        Card chosenCard = player.getCurrentHand().get(handIndex);
        int damageToEnemy = useCard(chosenCard);
        enemyCreature.takeDamage(damageToEnemy);
    }

    /**
     * Uses a card that is selected by the player
     *
     * @param usedCard Card that the player has selected
     * @return integer of the number of damage the selected card deals
     *
     * @author Nathan Ramkissoon
     */
    public int useCard(Card usedCard) {
        // Initialize to 0, because status and creature cards do no damage
        int damageNumber = 0;
        // Checks what type the card is, and calls the correct method
        switch (usedCard.getType()) {
            case CardType.STATUS:
                StatusCard usedStatusCard = (StatusCard) usedCard;
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
        return damageNumber;
    }
}
