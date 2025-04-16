/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/16/2025
 * Time: 2:13 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: AttackCard
 *
 * Description:
 *
 * ****************************************
 */

package cards;

public class AttackCard extends Card {
    private final int damage;

    private static final boolean IS_REUSABLE = false;
    private static final CardType cardType = CardType.ATTACK;

    /**
     * Creates an AttackCard using the Card constructor. This is the default constructor.
     * @param name a String representing the name of the card. Will be passed to the
     *             Card constructor.
     * @param image a String representing the image of the card. Will be passed to the
     *              Card constructor.
     * @param damage the amount of damage this card will deal, as an int.
     */
    public AttackCard(String name, String image, int damage) {
        super(name, image, cardType, IS_REUSABLE);
        this.damage = damage;
    }

    /**
     * Creates an AttackCard using the Card constructor. This is used by CreatureCard.
     * @param name a String representing the name of the card. Will be passed to the
     *             Card constructor.
     * @param image a String representing the image of the card. Will be passed to the
     *              Card constructor.
     * @param damage the amount of damage dealt by the creature in a single hit, as an int.
     * @param type a CardType object representing the type of card. Should be CREATURE.
     */
    public AttackCard(String name, String image, int damage, CardType type) {
        super(name, image, type, IS_REUSABLE);
        this.damage = damage;
    }

    public int useAttackCard() {
        return damage;
    }

    public int getDamage() {
        return damage;
    }
}