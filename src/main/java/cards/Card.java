/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/14/2025
 * Time: 2:42 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: Card
 *
 * Description:
 *
 * ****************************************
 */

package cards;

public abstract class Card {
    private String name;
    private String image;
    private final CardType cardType;
    private boolean reusable;

    /**
     * Constructs a new card object.
     * @param name a String for the name of the object.
     * @param image a String for the image file representing the card.
     * @param type the type of card that the new object is of, as an enum.
     * @param reusable boolean for whether the card is reusable.
     */
    public Card(String name, String image, CardType type, boolean reusable) {
        this.name = name;
        this.image = image;
        this.cardType = type;
        this.reusable = reusable;
    }

    public String getName() {
        return name;
    }

    public void makeNonReusable() throws IllegalArgumentException {
        if (this.cardType != CardType.STATUS) {
            throw new IllegalArgumentException("Method called on invalid instance. Only "
                    + "status cards should have variable reusability");
        }
        reusable = false;
    }

    public CardType getCardType() {
        return cardType;
    }
}
