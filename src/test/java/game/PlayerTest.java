/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/22/2025
 * Time: 10:43 AM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: PlayerTest
 *
 * Description:
 *
 * ****************************************
 */

package game;

import cards.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player player;
    Card card1;
    Card card2;
    Card card3;
    Card card4;

    @BeforeEach
    void setUp() {
        player = new Player(50);
        card1 = new StatusCard("Status Card", null, CardEffect.INCREASE_LOW);
        card2 = new AttackCard("Attack Card", null, 30);
        card3 = new CreatureCard("Creature 1", null, 10, 40);
        card4 = new CreatureCard("Creature 2", null, 20, 20);
        player.givePlayerCards(List.of(card1, card2, card3, card4));
    }

    /**
     * Checks if player starts alive
     */
    @Test
    void playerIsAlive() {
        assertTrue(player.getIfAlive());
        assertEquals(50, player.getCurrentHealth());
    }

    /**
     * Checks if player is dead when damage exactly reduces health to 0
     */
    @Test
    void playerIsDeadExactDamage() {
        player.takeDamage(50);
        assertFalse(player.getIfAlive());
        assertEquals(0, player.getCurrentHealth());
    }

    /**
     * Checks if player is dead when damage mathematically reduces health past 0
     */
    @Test
    void playerIsDeadOverDamage() {
        player.takeDamage(60);
        assertFalse(player.getIfAlive());
        assertEquals(0, player.getCurrentHealth());
    }

    /**
     * Checks if the player starts with no creatures
     */
    @Test
    void creatureListIsEmpty() {
        assertTrue(player.getSummonedCreatures().isEmpty());
    }

    /**
     * Checks if player starts with empty hand
     */
    @Test
    void currentHandIsEmpty() {
        assertTrue(player.getCurrentHand().isEmpty());
    }

    /**
     * Checks if player starts with a deck of cards
     */
    @Test
    void playerDeckHasCards() {
        assertFalse(player.getPlayerDeck().isEmpty());
        assertEquals(4, player.getPlayerDeck().size());
    }

    /**
     * Checks if the player draws the card on top of the queue
     */
    @Test
    void canDrawFromTop() {
        player.drawCard();
        assertEquals("Status Card", player.getCurrentHand().getFirst().getName());
        assertEquals(1, player.getCurrentHand().size());
    }
}
