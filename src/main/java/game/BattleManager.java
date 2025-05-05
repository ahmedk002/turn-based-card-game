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

/**
 * Manages battle state between a player and an enemy creature.
 * Tracks summoned creatures and determines if battle is won.
 *
 * @author Muhammad Ahmed
 */
public class BattleManager {
    private final Player player;
    private Creature enemyCreature;
    private boolean victoryStatus;
    private boolean defeatStatus;
    private final StringBuilder battleLog;

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
        this.battleLog = new StringBuilder();
    }

    /**
     * Starts the battle, which resets all battle statuses and the battle log.
     *
     * @author Muhammad Ahmed
     */
    public void startBattle() {
        victoryStatus = false;
        defeatStatus = false;
        battleLog.setLength(0);
        battleLog.append("Battle started! Enemy: ").append(enemyCreature.getName()).append("\n");
    }

    /**
     * The turn order that is executed upon the player selecting a card
     *
     * @param chosenCard Card that the user has selected for their turn
     *
     * @author Nathan Ramkissoon
     */
    public void battleTurns(Card chosenCard) {
        battleLog.setLength(0);
        playerTurn(chosenCard);
        checkVictory();
        enemyTurn();
        checkDefeat();
        player.removeDefeatedCreatures();
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

        battleLog.append("You played ").append(usedCard.getName()).append(".\n");

        // Initialized to 0, as status and creature cards do no damage
        int damageNumber = 0;
        // Checks what type the card is, and calls the correct method
        switch (usedCard.getType()) {
            case CardType.STATUS:
                StatusCard usedStatusCard = (StatusCard) usedCard;
                // If the card decreases damage, the effect is applied to the enemy
                // If it buffs damage or heals, the effect is instead applied to allied summoned creatures and the player
                String cardEffect = usedStatusCard.getCardEffect().getEffect().toLowerCase();
                if (cardEffect.contains("decrease")) {
                    usedStatusCard.useStatusCard(enemyCreature);

                    battleLog.append(enemyCreature.getName()).append("'s ")
                            .append(usedStatusCard.getCardEffect()).append("\n");
                } else {
                    for (Creature summonedCreature : player.getSummonedCreatures()) {
                        usedStatusCard.useStatusCard(summonedCreature);

                        battleLog.append(summonedCreature.getName()).append(" ")
                                .append(usedStatusCard.getCardEffect()).append("\n");
                    }
                    if (cardEffect.contains("heal")) {
                        player.heal(usedStatusCard.getCardEffect().getStrengthValue());

                        battleLog.append("Player ").append(usedStatusCard.getCardEffect()).append("\n");
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

                battleLog.append("You deal ").append(damageNumber).append(" damage.\n");
                break;
            case CardType.CREATURE:
                // If there are four summoned creatures already on the field,
                // then the creature card goes back into the deck
                if (player.getSummonedCreatures().size() < Player.getSummonedCreaturesLimit()) {
                    CreatureCard usedCreatureCard = (CreatureCard) usedCard;
                    // Gets a creature from the card's information and adds it to the summoned creature list
                    Creature creature = usedCreatureCard.useCreatureCard();
                    player.getSummonedCreatures().add(creature);

                    battleLog.append("You summoned ").append(creature.getName())
                            .append(" with ").append(creature.getDamage()).append(" damage and ")
                            .append(creature.getCurrentHealth()).append(" health.\n");
                } else {
                    player.getPlayerDeck().add(usedCard);

                    battleLog.append("You cannot summon more creatures");
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
     *
     * @param playerDamage the amount of damage that the player inflicts from an AttackCard.
     *                     Is 0 if no AttackCard was played.
     *
     * @author Riley Chen
     */
    private void attack(int playerDamage) {
        int totalDamage = playerDamage;
        enemyCreature.takeDamage(playerDamage);
        int creatureDamage;
        for (Creature creature : player.getSummonedCreatures()) {
            creatureDamage = creature.getDamage();
            enemyCreature.takeDamage(creatureDamage);
            totalDamage += creatureDamage;
        }

        battleLog.append("Enemy ").append(enemyCreature.getName())
                .append(" took ").append(totalDamage).append(" damage.\n");
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

        battleLog.append("Enemy ").append(enemyCreature.getName()).append(" attacked you for ")
                .append(enemyCreature.getDamage()).append(" damage.\n");
        if (!player.getSummonedCreatures().isEmpty()) {
            battleLog.append("Enemy attacked ally creatures for ")
                    .append(enemyCreature.getDamage()).append(" damage.\n");

            for (Creature summonedCreature : player.getSummonedCreatures()) {
                summonedCreature.takeDamage(enemyCreature.getDamage());
                if (!summonedCreature.isAlive()) {
                    battleLog.append(summonedCreature.getName()).append(" was defeated...\n");
                }
            }
        }
    }

    /**
     * Checks if the player is dead or their current hand is empty, meaning they ran out of cards
     */
    private void checkDefeat() {
        defeatStatus = !player.isAlive() ||
                (player.getSummonedCreatures().isEmpty() && player.getCurrentHand().isEmpty());
    }

    /**
     * Changes the enemy creature to a new creature
     *
     * @param newCreature the new enemy Creature
     */
    public void setNewCreature(Creature newCreature) {
        enemyCreature = newCreature;
    }

    public Player getPlayer() {
        return player;
    }

    public Creature getEnemyCreature() {
        return enemyCreature;
    }

    public boolean isVictory() {
        return victoryStatus;
    }

    public boolean isDefeated() { return defeatStatus; }

    /**
     * Gets the battle log of the current turn.
     *
     * @return the battle message log as a string
     *
     * @author Muhammad Ahmed
     */
    public String getBattleLog() {
        return battleLog.toString();
    }
}
