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
    private int health;

    public CreatureCard(String name, String image, CardType type, CardEffect effect, int damage, int health) {
        super(name, image, type, effect, damage);
        this.health = health;
    }

    /**
     * Uses a creature card to spawn a creature with the name, damage, and health attributes
     *     stored by the card.
     * @return the new Creature card.
     */
    public Creature useCreatureCard() {
        Creature creature = new Creature(this.getName(), this.getDamage(), health);
        return creature;
    }
}