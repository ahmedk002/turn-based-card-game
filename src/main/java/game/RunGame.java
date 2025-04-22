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

public class RunGame {
    public static void main(String[] args) {
        Player gamer = new Player(50);
        Creature goblin = new Creature("Goblin", 5, 300);

        Card fireball = new AttackCard("Fire Ball", null, 10);

        BattleManager battle = new BattleManager(gamer, goblin);
        battle.battleLoop();
    }
}
