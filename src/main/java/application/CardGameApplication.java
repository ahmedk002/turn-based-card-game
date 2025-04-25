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



import cards.*;
import creature.Creature;
import game.BattleManager;
import game.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;

public class CardGameApplication extends Application
{

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage1)
    {
        Pane root = new Pane();

        int handLocation = 550;
        int cardWidth = 120;
        int cardSpace = 30;
        int firstHandCardLocation = 255;
        int nextCardLocation = cardWidth + cardSpace;
        int creatureCardLocationX = 930;
        int firstCreatureCardLocationY = 0;
        double creatureScale = 0.75;

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
        for (int i = 0; i < gamer.getMaxCardsInHand(); i++) {
            battle.getPlayer().drawCard();
        }

        int enemyLocation = 290;
        ImageView enemy = new ImageView(goblin.getImage());
        enemy.relocate(enemyLocation, 0);
        root.getChildren().add(enemy);

        Scene scene1 = new Scene(root, 1080, 740);
        stage1.setScene(scene1);
        stage1.setTitle("Turn-Based Card Game");
        stage1.show();

        for (int i = 0; i < gamer.getCurrentHand().size(); i++) {
            Card card = gamer.getCurrentHand().get(i);
            ImageView cardImage = new ImageView(card.getImage());
            cardImage.relocate(firstHandCardLocation + nextCardLocation * i, handLocation);
            cardImage.setOnMouseClicked(event -> {
                battle.battleTurns(card);

                cardImage.setImage(new Image(gamer.getCurrentHand().getLast().getImage()));
            });
            root.getChildren().add(cardImage);
        }

        for (int i = 0; i < gamer.getSummonedCreatures().size(); i++) {
            ImageView cardImage = new ImageView(gamer.getSummonedCreatures().get(i).getImage());
            cardImage.setPreserveRatio(true);
            cardImage.setFitWidth(cardWidth * creatureScale);
            cardImage.relocate(creatureCardLocationX, firstCreatureCardLocationY  + nextCardLocation * i);
            root.getChildren().addAll(cardImage);
        }
    }
}