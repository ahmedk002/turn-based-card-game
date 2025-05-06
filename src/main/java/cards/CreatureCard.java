/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/16/2025
 * Time: 2:27 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: CreatureCard
 *
 * Description: Cards that do no damage but hold information used to create a Creature
 *
 * ****************************************
 */

package cards;

import game.Creature;

public class CreatureCard extends Card {
    private final int health;
    private final int damage;

    private static final CardType CARD_TYPE = CardType.CREATURE;
    private static final boolean IS_REUSABLE = false;

    /**
     * Constructor to create a new creature card.
     * @param name a String representing the name of the card. Will be passed to the
     *             Card constructor.
     * @param image a String representing the image of the card. Will be passed to the
     *              Card constructor.
     * @param damage the amount of damage dealt by the creature in a single hit, as an int.
     * @param health the max health of the creature, as an int.
     * @author Riley
     */
    public CreatureCard(String name, String image, int damage, int health) {
        super(name, image, CARD_TYPE, IS_REUSABLE);
        this.health = health;
        this.damage = damage;
    }

    /**
     * Uses a creature card to spawn a creature with the name, damage, and health attributes
     *     stored by the card.
     *
     * @return the new Creature
     *
     * @author Riley
     */
    public Creature useCreatureCard() {
        return new Creature(this.getName(), this.getImage(), this.getDamage(), health);
    }

    public int getDamage() {
        return damage;
    }
}