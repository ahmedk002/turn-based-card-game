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
 * Description:
 *
 * ****************************************
 */

package cards;

import creature.Creature;

public class CreatureCard extends AttackCard {
    private final int health;

    private static final CardType cardType = CardType.CREATURE;

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
        super(name, image, damage, cardType);
        this.health = health;
    }

    /**
     * Uses a creature card to spawn a creature with the name, damage, and health attributes
     *     stored by the card.
     * @return the new Creature card.
     * @author Riley
     */
    public Creature useCreatureCard() {
        Creature creature = new Creature(this.getName(), this.getDamage(), health);
        return creature;
    }
}