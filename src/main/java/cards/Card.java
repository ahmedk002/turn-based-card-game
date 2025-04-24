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
 * Description: Abstract class used as the basis for more specific Card objects
 *
 * ****************************************
 */

package cards;

public abstract class Card {
    private final String name;
    private final String image;
    private final CardType cardType;
    private boolean reusable;

    /**
     * Constructs a new card object.
     * @param name a String for the name of the object.
     * @param image a String for the image file representing the card.
     * @param type the type of card that the new object is of, as an enum.
     * @param reusable boolean for whether the card is reusable.
     * @author Riley
     */
    public Card(String name, String image, CardType type, boolean reusable) {
        this.name = name;
        this.image = image;
        this.cardType = type;
        this.reusable = reusable;
    }

    /**
     * Method used by StatusCard to set a card to non-reusable. Used when a player chooses
     *     to "burn" an upgrade on its first use to apply the full effect.
     * @throws IllegalArgumentException thrown if the card is not a StatusCard. Only StatusCards
     *     should have variable reusability.
     * @author Riley
     */
    public void makeNonReusable() throws IllegalArgumentException {
        if (this.cardType != CardType.STATUS) {
            throw new IllegalArgumentException("Method called on invalid instance. Only "
                    + "status cards should have variable reusability");
        }
        reusable = false;
    }

    public boolean isReusable() {
        return reusable;
    }

    public CardType getType() {
        return cardType;
    }

    public String getName() {
        return name;
    }

    public String getImage() { return image; }
}
