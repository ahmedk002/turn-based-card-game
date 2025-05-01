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

import java.util.ArrayList;
import java.util.List;

/**
 * Manages battle state between a player and an enemy creature.
 * Tracks summoned creatures and determines if battle is won.
 * @author Ahmed
 */
public class BattleManager {
    private final Player player;
    private final Creature enemyCreature;
    private boolean victoryStatus;
    private boolean defeatStatus;
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
        this.defeatStatus = false;
        this.summonedCreatures = player.getSummonedCreatures();
    }

    /**
     * Main loop that is repeated to simulate the battle
     *
     * @author Nathan Ramkissoon
     */
    public void battleTurns(Card chosenCard) {
        /*
        Unnecessary print statements.
        System.out.println("----------------------------------");
        System.out.println("Enemy health: " + enemyCreature.getHealth() + "\nPlayer Health: " + player.getCurrentHealth());
        for (Creature creature : summonedCreatures) {
            //System.out.println(creature.getName() + " health: " + creature.getHealth());
        }
        */
        playerTurn(chosenCard);
        checkVictory();
        enemyTurn();
        checkDefeat();
        removeDefeatedCreatures();
    }

    /**
     * Displays game information if the game is being played using the terminal.
     * @author Nathan Ramkissoon
     */
    public void battleLoop() {
        System.out.println("----------------------------------");
        System.out.println("Enemy health: " + enemyCreature.getHealth() + "\nPlayer Health: " + player.getCurrentHealth());
        for (Creature creature : summonedCreatures) {
            System.out.println(creature.getName() + " health: " + creature.getHealth());
        }
        attack(0);
        checkVictory();
        enemyTurn();
        checkDefeat();
        removeDefeatedCreatures();
    }

    /**
     * Starts the battle — could later be expanded into turn-based logic.
     *
     * @author Muhammad Ahmed
     */
    public void startBattle() {
        victoryStatus = false;
        defeatStatus = false;
        System.out.println("Battle started between player and enemy: " + enemyCreature.getName());
    }

    /**
     * Player chooses a card and plays it, which may cause damage to the enemy
     *
     * @author Nathan Ramkissoon
     */
    private void playerTurn(Card chosenCard) {
        int damageToEnemy = useCard(chosenCard);
        attack(damageToEnemy);
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
        // usedCard is null when player runs out of cards or has skipped their turn
        if (usedCard == null) {
            return 0;
        }

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
                if (!usedStatusCard.isReusable() && usedCard.isReusable()) {
                    usedCard.makeNonReusable();
                }
                break;
            case CardType.ATTACK:
                AttackCard usedAttackCard = (AttackCard) usedCard;
                // The attack card's damage is collected
                damageNumber = usedAttackCard.useAttackCard();
                break;
            case CardType.CREATURE:
                // If there are four summoned creatures already on the field,
                // then the creature card goes back into the deck
                if (player.getSummonedCreatures().size() < player.getSummonedCreaturesLimit()) {
                    CreatureCard usedCreatureCard = (CreatureCard) usedCard;
                    // Gets a creature from the card's information and adds it to the summoned creature list
                    Creature creature = usedCreatureCard.useCreatureCard();
                    player.getSummonedCreatures().add(creature);
                } else {
                    player.getPlayerDeck().add(usedCard);
                }
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
     * Attacks the enemy creature. Both the attack from an AttackCard (if played) and
     *     the attacks of all summoned creatures will be applied by this method.
     * @param playerDamage the amount of damage that the player inflicts from an AttackCard.
     *                     Is 0 if no AttackCard was played.
     */
    private void attack(int playerDamage) {
        enemyCreature.takeDamage(playerDamage);
        System.out.println(enemyCreature.getName() + " took " + playerDamage + " damage");
        int creatureDamage;
        for (Creature creature : player.getSummonedCreatures()) {
            creatureDamage = creature.getDamage();
            enemyCreature.takeDamage(creatureDamage);
            System.out.println(creature.getName() + " attacks " + enemyCreature.getName() + "!\n"
                    + enemyCreature.getName() + " took " + creatureDamage + " damage.");
        }
    }

    /**
     * Checks if the enemy creature has been defeated.
     *
     * @author Muhammad Ahmed
     */
    private void checkVictory() {
        if (!enemyCreature.isAlive()) {
            victoryStatus = true;
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

    /**
     * Checks if the player is dead or their current hand is empty, meaning they ran out of cards
     */
    public void checkDefeat() {
        defeatStatus = !player.getIfAlive() ||
                (player.getSummonedCreatures().isEmpty() && player.getCurrentHand().isEmpty());
    }

    /**
     * Removes creatures from the summoned creatures list if they are defeated
     *
     * @author Nathan Ramkissoon
     */
    private void removeDefeatedCreatures() {
        if (!summonedCreatures.isEmpty()) {
            List<Creature> aliveCreatures = new ArrayList<>();
            for (Creature summonedCreature : summonedCreatures) {
                if (summonedCreature.isAlive()) {
                    aliveCreatures.add(summonedCreature);
                } else {
                    System.out.println(summonedCreature.getName() + " was defeated...");
                }
            }
            summonedCreatures = aliveCreatures;
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

    public boolean isDefeat() { return defeatStatus; }
}
