package application;/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/24/2025
 * Time: 1:24 PM
 *
 * Project: csci205_final_project
 * Package: PACKAGE_NAME
 * Class: application.CardGameApplication
 *
 * Description:
 *
 * ****************************************
 */

import application.view.CardGameView;
import cards.*;
import creature.Creature;
import game.BattleManager;
import game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class CardGameApplication extends Application {

    private CardGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage1) {
        Player gamer = new Player(50);
        Creature goblin = new Creature("Goblin", "generic_slime.png", 5, 300);

        Card increaseDamageLow = new StatusCard("Low Damage Increase", "blank_buff_card.png", CardEffect.INCREASE_LOW);
        Card decreaseDamageLow = new StatusCard("Low Damage Decrease", "blank_debuff_card.png", CardEffect.DECREASE_LOW);
        Card fireBall = new AttackCard("Fire Ball", "blank_attack_card.png", 10);
        Card thunderBolt = new AttackCard("Thunder Bolt", "blank_attack_card.png", 10);
        Card lion = new CreatureCard("Lion", "blank_creature_card.png", 10, 40);
        Card chimera = new CreatureCard("Chimera", "blank_creature_card.png", 25, 60);
        gamer.givePlayerCards(List.of(fireBall, thunderBolt, increaseDamageLow, lion, decreaseDamageLow, chimera));

        BattleManager battle = new BattleManager(gamer, goblin);

        view = new CardGameView(battle);
        Scene scene1 = new Scene(view.getRoot(), 1080, 740);
        stage1.setTitle("Turn-Based Card Game");
        stage1.setScene(scene1);
        stage1.show();
    }
}