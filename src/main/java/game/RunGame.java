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
 * Description: A command-line version of the game runner for testing the battle system.
 * Allows a player to use cards turn-by-turn until victory or defeat.
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
        Scanner scanner = new Scanner(System.in);

        Player gamer = new Player(50);
        Creature goblin = new Creature("Goblin", null, 5, 300);

        Card increaseDamageLow = new StatusCard("Sharpened claw", null, CardEffect.INCREASE_LOW, false);
        Card decreaseDamageLow = new StatusCard("Dulling acid", null, CardEffect.DECREASE_LOW, false);
        Card fireBall = new AttackCard("Fire Ball", null, 10);
        Card thunderBolt = new AttackCard("Thunder Bolt", null, 10);
        Card lion = new CreatureCard("Lion", null, 10, 40);
        Card chimera = new CreatureCard("Chimera", null, 25, 60);
        gamer.givePlayerCards(List.of(fireBall, thunderBolt, increaseDamageLow, lion, decreaseDamageLow, chimera));
        BattleManager battle = new BattleManager(gamer, goblin);

        for (int i = 0; i < Player.getMaxCardsInHand(); i++) {
            gamer.drawCard();
        }

        battle.startBattle();
        System.out.println("=== Welcome to the Battle! ===");
        System.out.println("Enemy: " + goblin.getName());

        while (!battle.isDefeated()) {
            System.out.println("\nYour current hand:");
            List<Card> hand = gamer.getCurrentHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println((i + 1) + ". " + hand.get(i).getName());
            }

            System.out.print("Choose a card to use (1-" + hand.size() + "): ");
            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice < 1 || choice > hand.size()) {
                    System.out.println("Invalid choice. Try again.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            Card selectedCard = hand.get(choice - 1);
            battle.battleTurns(selectedCard);

            System.out.println("\n--- Turn Result ---");
            System.out.println(battle.getBattleLog());
            System.out.println("Player Health: " + gamer.getCurrentHealth());
            System.out.println("Enemy Health: " + goblin.getCurrentHealth());
        }

        if (battle.isVictory()) {
            System.out.println("\n>>> You won the battle! <<<");
        } else {
            System.out.println("\n>>> Game Over. You were defeated. <<<");
        }
    }
}
