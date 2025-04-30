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
 * Description: Runs the actual game in the terminal
 *
 * ****************************************
 */

import cards.*;
import creature.*;

import java.util.List;
import java.util.Scanner;

/**
 * Deprecated class originally used to play the game using the terminal. Use CardGameApplication instead!!!
 */
public class RunGame {
    public static void main(String[] args) {
        Player gamer = new Player(50);
        Creature goblin = new Creature("Goblin", null, 5, 300);

        Card increaseDamageLow = new StatusCard("Sharpened claw", null, CardEffect.INCREASE_LOW);
        Card decreaseDamageLow = new StatusCard("Dulling acid", null, CardEffect.DECREASE_LOW);
        Card fireBall = new AttackCard("Fire Ball", null, 10);
        Card thunderBolt = new AttackCard("Thunder Bolt", null, 10);
        Card lion = new CreatureCard("Lion", null, 10, 40);
        Card chimera = new CreatureCard("Chimera", null, 25, 60);
        gamer.givePlayerCards(List.of(fireBall, thunderBolt, increaseDamageLow, lion, decreaseDamageLow, chimera));

        for (int i = 0; i < gamer.getMaxCardsInHand(); i++) {
            gamer.drawCard();
        }

        BattleManager battle = new BattleManager(gamer, goblin);
        battle.startBattle();
        while (!battle.isDefeat()) {
            if (!gamer.getCurrentHand().isEmpty()) {
                System.out.println("Choose card:");
                Scanner scan = new Scanner(System.in);
                int handIndex = scan.nextInt();
                Card chosenCard = battle.getPlayer().getCurrentHand().get(handIndex);
                battle.battleTurns(chosenCard);
            } else {
                battle.battleTurns(null);
            }

        }
    }
}
