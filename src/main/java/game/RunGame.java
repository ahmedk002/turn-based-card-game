package game;/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/22/2025
 * Time: 10:38 AM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: RunGame
 *
 * Description:
 *
 * ****************************************
 */

import cards.*;
import creature.*;

import java.util.List;

public class RunGame {
    public static void main(String[] args) {
        Player gamer = new Player(50);
        Creature goblin = new Creature("Goblin", 5, 300);

        Card increaseDamageLow = new StatusCard("Sharpened claw", null, CardEffect.INCREASE_LOW);
        Card decreaseDamageLow = new StatusCard("Dulling acid", null, CardEffect.DECREASE_LOW);
        Card fireBall = new AttackCard("Fire Ball", null, 10);
        Card thunderBolt = new AttackCard("Thunder Bolt", null, 10);
        Card lion = new CreatureCard("Lion", null, 10, 40);
        Card chimera = new CreatureCard("Chimera", null, 25, 60);
        gamer.givePlayerCards(List.of(fireBall, thunderBolt, increaseDamageLow, lion, decreaseDamageLow, chimera));

        BattleManager battle = new BattleManager(gamer, goblin);
        battle.battleLoop();
    }
}
