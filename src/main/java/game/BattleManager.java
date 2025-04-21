/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Muhammad Ahmed
 * Date: 4/14/2025
 * Time: 2:43 PM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: Player
 *
 * Description:
 *
 * ****************************************
 */

package game;

import cards.Card;
import creature.Creature;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class BattleManager {
    private final Player player;
    private final Creature enemyCreature;
    private boolean victoryStatus;
    private final List<Creature> summonedCreatures;

    public BattleManager(Player player, Creature enemyCreature) {
        this.player = player;
        this.enemyCreature = enemyCreature;
        this.victoryStatus = false;
        this.summonedCreatures = new ArrayList<>();
    }

    public void startBattle() {
        System.out.println("Battle started between player and " + enemyCreature.getName());
    }

    public boolean checkVictory() {
        if (!enemyCreature.isAlive()) {
            victoryStatus = true;
        }
        return victoryStatus;
    }

    public void updateBattleState() {
        // You can add logic to simulate one round, or update turn state
        System.out.println("Updating battle state...");
        if (!enemyCreature.isAlive()) {
            System.out.println("Enemy defeated!");
            victoryStatus = true;
        }
    }

    public void playerTurn() {
        Scanner scanner = new Scanner(System.in);
        int handIndex = scanner.nextInt();
        Card chosenCard = player.getCurrentHand().get(handIndex);
        int damageToEnemy = player.useCard(chosenCard);
        enemyCreature.takeDamage(damageToEnemy);
    }
}
