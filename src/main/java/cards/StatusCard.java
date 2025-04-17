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
     * @param creatureList the list of creatures that the player has.
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
                applyModerateEffect(creatureList);
            }
        }
        applyModerateEffect(creatureList);
    }

    private void applyStrongEffect(List<Creature> creatureList) {
        String upgrade = effect.getEffect();
        int strength = effect.getStrengthValue();
        applyEffect(creatureList, upgrade, strength);
    }

    private void applyModerateEffect(List<Creature> creatureList) {
        String upgrade = effect.getEffect();
        int strength= effect.getStrengthValue() / 2;
        applyEffect(creatureList, upgrade, strength);
    }

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