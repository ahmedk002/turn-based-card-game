/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/22/2025
 * Time: 10:43 AM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: PlayerTest
 *
 * Description:
 *
 * ****************************************
 */

import cards.*;
import game.*;
import org.junit.jupiter.api.BeforeEach;

public class PlayerTest {

    @BeforeEach
    void setUp() {
        Player player = new Player(50);
        Card card1 = new StatusCard("Attack Card", null, CardEffect.INCREASE_LOW);
        Card card2 = new AttackCard("Status Card", null, 30);
        Card card3 = new CreatureCard("Creature 1", null, 10, 40);
        Card card4 = new CreatureCard("Creature 2", null, 20, 20);
    }
}
