/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/24/2025
 * Time: 1:23 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: StatusCardTest
 *
 * Description:
 *
 * ****************************************
 */

package cards;

import creature.Creature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusCardTest {
    Creature creature;
    Card damageIncreaseCard1;
    Card damageDecreaseCard1;
    Card healCard1;
    Card damageIncreaseCard2;
    Card damageDecreaseCard2;
    Card healCard2;

    final CardEffect effect1 = CardEffect.INCREASE_MED;
    final CardEffect effect2 = CardEffect.DECREASE_MED;
    final CardEffect effect3 = CardEffect.HEAL_MED;

    final int playerHealth = 50;

    @BeforeEach
    void setUp() {
        creature = new Creature("Friendly Lion", null, 10, 10);

        //Create a set of three reusable StatusCards (one with each effect).
        damageIncreaseCard1 = new StatusCard("Sharpened claw", null, effect1, true);
        damageDecreaseCard1 = new StatusCard("Broken bones", null, effect2, true);
        healCard1 = new StatusCard("Medkit", null, effect3, true);

        //Create a set of three non-reusable StatusCards (one with each effect);
        damageIncreaseCard2 = new StatusCard("Sharpened claw", null, effect1, false);
        damageDecreaseCard2 = new StatusCard("Broken bones", null, effect2, false);
        healCard2 = new StatusCard("Medkit", null, effect3, false);
    }

    @Test
    void testReusableDamageIncrease() {
        assertEquals(10, creature.getDamage());
        StatusCard card = (StatusCard) damageIncreaseCard1;
        card.useStatusCard(creature);
        assertEquals(15, creature.getDamage()); //Test applying the card.
        card.useStatusCard(creature);
        assertEquals(20, creature.getDamage()); //Make sure you can apply the card again.
    }

    @Test
    void testNonreusableDamageIncrease() {
        assertEquals(10, creature.getDamage());
        StatusCard card = (StatusCard) damageIncreaseCard2;
        card.useStatusCard(creature);
        assertEquals(15, creature.getDamage());
        assertFalse(card.isReusable());
    }

    @Test
    void testReusableDamageDecrease() {
        assertEquals(10, creature.getDamage());
        StatusCard card = (StatusCard) damageDecreaseCard1;
        card.useStatusCard(creature);
        assertEquals(5, creature.getDamage());
        card.useStatusCard(creature);
        assertEquals(1, creature.getDamage());
    }

    @Test
    void testNonreusableDamageDecrease() {
        assertEquals(10, creature.getDamage());
        StatusCard card = (StatusCard) damageDecreaseCard2;
        card.useStatusCard(creature);
        assertEquals(5, creature.getDamage());
        assertFalse(card.isReusable());
    }

    @Test
    void testHeal() {
        assertEquals(10, creature.getCurrentHealth());
        StatusCard card = (StatusCard) healCard1;

        //Ensure that the creature cannot heal above its max health.
        card.useStatusCard(creature);
        assertEquals(10, creature.getCurrentHealth());

        /*
        Damage creature first, then heal it by greater than the amount of damage taken.
        Ensure that it only heals to its max health.
        */
        creature.takeDamage(1);
        card.useStatusCard(creature);
        assertEquals(10, creature.getCurrentHealth());

        //Deal a lot of damage to the creature to test the exact amount it heals.
        creature.takeDamage(8);
        card.useStatusCard(creature);
        assertEquals(7, creature.getCurrentHealth());
    }

    @Test
    void testNonreusableHeal() {
        assertEquals(10, creature.getCurrentHealth());
        creature.takeDamage(6);
        StatusCard card = (StatusCard) healCard2;
        card.useStatusCard(creature);
        assertEquals(9, creature.getCurrentHealth());
    }
}