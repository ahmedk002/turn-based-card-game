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

import creature.Creature;

import java.util.ArrayList;
import java.util.List;

public class BattleManager<Player> {
    private Player player;
    private Creature enemyCreature;
    private boolean victoryStatus;
    private List<Creature> summonedCreatures;

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
}
