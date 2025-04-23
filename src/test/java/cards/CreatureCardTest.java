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

import creature.Creature;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CreatureCardTest {
    Player player;
    Card card1;

    final int damage = 10;
    final int health = 10;

    @BeforeEach
    void setUp() {
        player = new Player(50);
        card1 = new CreatureCard("Lion", null, damage, health);
    }

    @Test
    void spawnCreature() {
        CreatureCard creatureCard = (CreatureCard) card1;
        Creature lion = creatureCard.useCreatureCard();
        assertFalse(lion.equals(null));
        assertTrue(lion.getName().equals("Lion"));
        assertTrue(lion.getDamage() == 10);
        assertTrue(lion.getHealth() == 10);
    }
}