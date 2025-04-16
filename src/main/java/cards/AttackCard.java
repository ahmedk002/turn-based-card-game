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
    private int damage;
    private static final boolean IS_REUSABLE = false;

    public AttackCard(String name, String image, CardType type, CardEffect effect, int damage) {
        super(name, image, type, effect, IS_REUSABLE);
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}