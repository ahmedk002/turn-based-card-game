/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Riley Chen
 * Date: 4/23/2025
 * Time: 2:33 PM
 *
 * Project: csci205_final_project
 * Package: cards
 * Class: AttackCardTest
 *
 * Description:
 *
 * ****************************************
 */

package cards;

import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttackCardTest {
    Player player;
    Card card1;

    final int playerHealth = 50;
    final int damage = 10;

    @BeforeEach
    void setUp() {
        player = new Player(playerHealth);
        card1 = new AttackCard("Thunderbolt", null, damage);
    }

    @Test
    void testUseCard() {
        AttackCard thunderbolt = (AttackCard) card1;
        int damageDealt = thunderbolt.useAttackCard();
        assertTrue(damageDealt == damage);
    }
}