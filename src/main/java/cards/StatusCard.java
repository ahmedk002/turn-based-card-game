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
 * Description: Cards that do no damage and instead apply an effect to creatures
 *
 * ****************************************
 */

package cards;

import game.Creature;

public class StatusCard extends Card {
    private final CardEffect cardEffect;

    private static final CardType CARD_TYPE = CardType.STATUS;

    /**
     * Constructor for a new StatusCard.
     * @param name a String for the name of the card.
     * @param image a String for the image of the card.
     * @param effect a CardEffect enum representing the effect the card has.
     */
    public StatusCard (String name, String image, CardEffect effect, boolean reusable) {
        super(name, image, CARD_TYPE, reusable);
        this.cardEffect = effect;
    }

    /**
     * Uses a status card to apply an upgrade to each one of the player's creatures.
     * Currently only applies a card's maximum effect
     *
     * @param creature Creature that the effect will be applied to
     */
    public void useStatusCard(Creature creature) {
        applyStrongEffect(creature);
    }

    /**
     * Gets the details and strength of the status card's effect.
     *     Upgrade -> gotten from the getEffect() method. May be a damage upgrade, a
     *     damage nerf, or heal.
     *     Strength -> the strength of the upgrade, gotten from the getStrengthValue() method.
     *     applyEffect() -> another helper method that will apply the effect to each creature.
     *
     * @param creature the creature the effect will be applied to.
     *
     * @author Riley
     */
    private void applyStrongEffect(Creature creature) {
        String upgrade = cardEffect.getEffect();
        int strength = cardEffect.getStrengthValue();
        applyEffect(creature, upgrade, strength);
    }

    /**
     * Gets the details and strength of the status card's effect.
     *     Upgrade -> gotten from the getEffect() method. May be a damage upgrade, a
     *     damage nerf, or heal.
     *     Strength -> the strength of the upgrade, gotten from the getStrengthValue() method.
     *     The strength is half of what the method returns, since it is only a moderate effect.
     *     applyEffect() -> another helper method that will apply the effect to each creature.
     *
     * @param creature the creature the effect will be applied to.
     *
     * @author Riley
     */
    private void applyModerateEffect(Creature creature) {
        String upgrade = cardEffect.getEffect();
        int strength= cardEffect.getStrengthValue() / 2;
        applyEffect(creature, upgrade, strength);
    }

    /**
     * Applies the effect and strength to the creatures in the list.
     * @param creature the creature that the buff/debuff will be applied to.
     * @param upgrade a String explaining what the effect does.
     * @param strength an int representing the strength of the effect.
     * @throws IllegalArgumentException if the upgrade String is not one of the three possible
     *     upgrades.
     * @author Riley
     */
    private void applyEffect(Creature creature, String upgrade, int strength)
            throws IllegalArgumentException {
        switch (upgrade) {
            case "damage increases" -> creature.increaseDamage(strength);
            case "damage decreases" -> creature.decreaseDamage(strength);
            case "heals" -> creature.heal(strength);
            default -> throw new IllegalArgumentException("Unknown upgrade: " + upgrade);
        }
    }

    public CardEffect getCardEffect() {
        return cardEffect;
    }
}