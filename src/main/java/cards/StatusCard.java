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

import creature.Creature;
import java.util.List;
import java.util.Scanner;

public class StatusCard extends Card {
    private boolean used;
    private final Scanner sc;
    private final CardEffect effect;

    private static final boolean IS_REUSABLE = true;
    private static final CardType cardType = CardType.STATUS;

    public StatusCard (String name, String image, CardType type, CardEffect effect) {
        super(name, image, type, IS_REUSABLE);
        this.effect = effect;
        used = false;
        sc = new Scanner(System.in);
    }

    /**
     * Uses a status card to apply an upgrade to each one of the player's creatures.
     *     If this is the first use, the function first asks if the player wants to
     *     apply a strong or moderate effect. The strong effect results in the card
     *     being immediately destroyed, while the moderate effect is half-strength but
     *     the card is not destroyed.
     *     Uses helper methods to get the effect and apply it to each creature.
     * @param creatureList the list of creatures that the player has.
     * @author Riley
     */
    public void useStatusCard(List<Creature> creatureList) {
        if (!used) {
            /*
            Ask if the player wants to apply the max strength (non-reusable) or the moderate strength
            (reusable) status.
             */
            System.out.println("Would you like to apply a maximum strength effect? [Y/N]\n"
                    + "WARNING!!! This will destroy the card. ");
            String input = sc.nextLine().toUpperCase();
            while (!validInput(input)) {
                System.out.println("Invalid input! Try again.");
                input = sc.nextLine().toUpperCase();
            }

            if (input.equals("Y")) {
                //Apply maximum strength effect and make card non-reusable.
                makeNonReusable();
                applyStrongEffect(creatureList);
            } else {
                //Apply the moderate effect.
                applyModerateEffect(creatureList);
            }
            used = true;
        } else {
            applyModerateEffect(creatureList);
        }
    }

    /**
     * Gets the details and strength of the status card's effect.
     *     Upgrade -> gotten from the getEffect() method. May be a damage upgrade, a
     *     damage nerf, or a heal.
     *     Strength -> the strength of the upgrade, gotten from the getStrengthValue() method.
     *     applyEffect() -> another helper method that will apply the effect to each creature.
     * @param creatureList the list of creatures that the effect will be applied to.
     * @author Riley
     */
    private void applyStrongEffect(List<Creature> creatureList) {
        String upgrade = effect.getEffect();
        int strength = effect.getStrengthValue();
        applyEffect(creatureList, upgrade, strength);
    }

    /**
     * Gets the details and strength of the status card's effect.
     *     Upgrade -> gotten from the getEffect() method. May be a damage upgrade, a
     *     damage nerf, or a heal.
     *     Strength -> the strength of the upgrade, gotten from the getStrengthValue() method.
     *     The strength is half of what the method returns, since it is only a moderate effect.
     *     applyEffect() -> another helper method that will apply the effect to each creature.
     * @param creatureList the list of creatures that the effect will be applied to.
     * @author Riley
     */
    private void applyModerateEffect(List<Creature> creatureList) {
        String upgrade = effect.getEffect();
        int strength= effect.getStrengthValue() / 2;
        applyEffect(creatureList, upgrade, strength);
    }

    /**
     * Applies the effect and strength to the creatures in the list.
     * @param creatureList the list of creatures that will be affected by the upgrade.
     * @param upgrade a String explaining what the effect does.
     * @param strength an int representing the strength of the effect.
     * @throws IllegalArgumentException if the upgrade String is not one of the three possible
     *     upgrades.
     * @author Riley
     */
    private void applyEffect(List<Creature> creatureList, String upgrade, int strength)
            throws IllegalArgumentException {
        for (Creature creature : creatureList) {
            switch (upgrade) {
                case "damage decreases" -> creature.getDamage() -= strength;
                case "damage increases" -> creature.getDamage() += strength;
                case "heals" -> creature.getHealth() += strength;
                default -> throw new IllegalArgumentException("Unknown upgrade: " + upgrade);
            }
        }
    }

    private boolean validInput(String input) {
        if (input.equals("Y") || input.equals("N")) {
            return true;
        } else {
            return false;
        }
    }
}