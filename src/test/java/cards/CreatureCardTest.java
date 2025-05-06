/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/23/2025
 * Time: 2:19 PM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: CreatureCardTest
 *
 * Description:
 *
 * ****************************************
 */

package cards;

import game.Creature;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreatureCardTest {
    Player player;
    Card card1;
    Card card2;
    Card card3;

    final int playerHealth = 50;

    @BeforeEach
    void setUp() {
        player = new Player(playerHealth);
        card1 = new CreatureCard("Lion", null, 10, 10);
        card2 = new CreatureCard("Pigeon", null, 5, 5);
        card3 = new CreatureCard("Shark", null, 5, 10);
        player.givePlayerCards(List.of(card1, card2, card3));
    }

    @Test
    void testSpawnCreature() {
        CreatureCard lionCard = (CreatureCard) card1;
        Creature lion = lionCard.useCreatureCard();
        assertFalse(lion.equals(null));
        assertTrue(lion.getName().equals("Lion"));
        assertTrue(lion.getDamage() == 10);
        assertTrue(lion.getCurrentHealth() == 10);

        CreatureCard pigeonCard = (CreatureCard) card2;
        Creature pigeon = pigeonCard.useCreatureCard();
        assertFalse(pigeon.equals(null));
        assertTrue(pigeon.getName().equals("Pigeon"));
        assertTrue(pigeon.getDamage() == 5);
        assertTrue(pigeon.getCurrentHealth() == 5);

        CreatureCard sharkCard = (CreatureCard) card3;
        Creature shark = sharkCard.useCreatureCard();
        assertFalse(shark.equals(null));
        assertTrue(shark.getName().equals("Shark"));
        assertTrue(shark.getDamage() == 5);
        assertTrue(shark.getCurrentHealth() == 10);
    }
}