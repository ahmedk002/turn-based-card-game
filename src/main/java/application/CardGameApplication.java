package application;/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/24/2025
 * Time: 1:24 PM
 *
 * Project: csci205_final_project
 * Package: application
 * Class: CardGameApplication
 *
 * Description:
 *
 * ****************************************
 */

import application.view.CardGameView;
import cards.*;
import game.Creature;
import game.BattleManager;
import game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class CardGameApplication extends Application {

    private CardGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage1) {
        Player gamer = new Player(75);
        Creature slime = new Creature("Slime", "generic_slime.png", 2, 20);

        Card razorSharpen = new StatusCard("Razor Sharpen", "razorsharpen.png", CardEffect.INCREASE_LOW, false);
        Card weakeningPoison = new StatusCard("Weakening Poison", "weakeningpoison.png", CardEffect.DECREASE_LOW, false);
        Card fireBall = new AttackCard("Fire Ball", "fireball.png", 10);
        Card thunderStorm = new AttackCard("Thunder Storm", "thunderstorm.png", 10);
        Card florarsun = new CreatureCard("Florasun", "florasun.png", 5, 10);
        Card winterWarbler = new CreatureCard("Winter Warbler", "winterwarbler.png", 12, 15);
        gamer.givePlayerCards(List.of(florarsun, weakeningPoison, fireBall, thunderStorm, winterWarbler, razorSharpen));

        BattleManager battle = new BattleManager(gamer, slime);

        view = new CardGameView(battle);
        Scene scene1 = new Scene(view.getRoot(), 1080, 740);
        stage1.setTitle("Turn-Based Card Game");
        stage1.setScene(scene1);
        stage1.show();
    }
}