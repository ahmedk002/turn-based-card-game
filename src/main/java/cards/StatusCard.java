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

    private void applyEffect(List<Creature> creatureList, String upgrade, int strength) {
        for (Creature creature : creatureList) {
            if (upgrade.equals("damage decreases")) {
                creature.getDamage() -= strength;
            } else if (upgrade.equals("damage increases")) {
                creature.getDamage() += strength;
            } else if (upgrade.equals("heals")) {
                creature.getHealth() += strength;
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