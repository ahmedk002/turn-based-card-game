/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/16/2025
 * Time: 3:00 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: StatusCard
 *
 * Description:
 *
 * ****************************************
 */

package cards;

public class StatusCard extends Card {
    private final CardEffect effect;

    private static final boolean IS_REUSABLE = true;
    private static final CardType cardType = CardType.STATUS;

    public StatusCard (String name, String image, CardType type, CardEffect effect) {
        super(name, image, type, IS_REUSABLE);
        this.effect = effect;
    }
}