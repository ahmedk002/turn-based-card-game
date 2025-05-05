/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/28/2025
 * Time: 1:05 PM
 *
 * Project: csci205_final_project
 * Package: application.view
 * Class: CardGameView
 *
 * Description:
 *
 * ****************************************
 */

package application.view;

import cards.*;
import creature.Creature;
import game.BattleManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class CardGameView {
    private Pane root;
    private Label playerHealthLabel;
    private Label enemyHealthLabel;
    private Button skipButton;
    private ImageView enemy;
    private HBox handDisplay;
    private VBox summonedCreaturesDisplay;
    private VBox choiceBox;
    private Label continueMessage;
    private Label gameOver;
    private TextArea battleLog;
    private Button yesButton;
    private Button noButton;

    private final BattleManager battle;
    private final List<Card> cardDeck;
    private int wins;

    private final static int HAND_LOCATION = 550;
    private final static int CARD_WIDTH = 120;
    private final static int CARD_SPACE = 30;
    private final static int FIRST_HAND_CARD_LOCATION = 255;
    private final static int CREATURES_LOCATION = 930;
    private final static int FIRST_CREATURE_LOCATION = 0;
    private final static double CREATURE_SCALE = 0.75;
    private final static int ENEMY_LOCATION = 290;

    public CardGameView(BattleManager battle) {
        this.battle = battle;
        this.cardDeck = new ArrayList<>(List.copyOf(battle.getPlayer().getPlayerDeck()));
        initializeView();
    }

    public Pane getRoot() {
        return root;
    }

    /**
     * Creates the initial battle screen.
     *
     * @author Nathan Ramkissoon & Muhammad Ahmed
     */
    public void initializeView() {
        root = new Pane();

        battle.startBattle();

        battle.getPlayer().drawInitialCards();

        // Enemy setup
        enemy = new ImageView(battle.getEnemyCreature().getImage());
        enemy.relocate(ENEMY_LOCATION, 0);
        root.getChildren().add(enemy);

        // Health labels
        playerHealthLabel = new Label("Player Health: " + battle.getPlayer().getCurrentHealth());
        playerHealthLabel.relocate(50, 20);
        root.getChildren().add(playerHealthLabel);

        enemyHealthLabel = new Label("Enemy Health: " + battle.getEnemyCreature().getCurrentHealth());
        enemyHealthLabel.relocate(800, 20);
        root.getChildren().add(enemyHealthLabel);

        // Battle log
        battleLog = new TextArea();
        battleLog.setEditable(false);
        battleLog.setWrapText(true);
        battleLog.setPrefSize(250, 200);
        battleLog.relocate(50, 60);
        root.getChildren().add(battleLog);

        // Skip button
        skipButton = new Button("SKIP TURN");
        skipButton.setOnMousePressed(event -> {
            battle.battleTurns(null);
            updateBattleScreen();
        });
        skipButton.relocate(50,600);
        root.getChildren().add(skipButton);

        // Cards in hand
        handDisplay = new HBox(CARD_SPACE);
        handDisplay.relocate(FIRST_HAND_CARD_LOCATION, HAND_LOCATION);
        root.getChildren().add(handDisplay);

        // Summoned creatures
        summonedCreaturesDisplay = new VBox(CARD_SPACE);
        summonedCreaturesDisplay.relocate(CREATURES_LOCATION, FIRST_CREATURE_LOCATION);
        root.getChildren().add(summonedCreaturesDisplay);

        // Continuation option
        choiceBox = new VBox(10);
        continueMessage = new Label("Would you like to try again?");
        gameOver = new Label("Game Over");
        gameOver.relocate(400, 500);
        yesButton = new Button("Yes");
        yesButton.setOnMouseClicked(event -> restartBattle());
        noButton = new Button("No");
        noButton.setOnMouseClicked(event -> {
            choiceBox.setVisible(false);
            root.getChildren().add(gameOver);
        });
        choiceBox.getChildren().addAll(List.of(continueMessage, yesButton, noButton));
        choiceBox.relocate(400, 500);
        root.getChildren().add(choiceBox);
        choiceBox.setVisible(false);

        updateBattleScreen();
    }

    /**
     * Updates the battle screen with the latest information.
     *
     * @author Muhammad Ahmed & Nathan Ramkissoon
     */
    private void updateBattleScreen() {
        // Update health labels
        playerHealthLabel.setText("Player Health: " + battle.getPlayer().getCurrentHealth());
        enemyHealthLabel.setText("Enemy Health: " + battle.getEnemyCreature().getCurrentHealth());

        // Update cards
        handDisplay.getChildren().clear();
        for (Card card : battle.getPlayer().getCurrentHand()) {
            ImageView cardImage = new ImageView(new Image(card.getImage()));

            cardImage.setOnMouseClicked(event -> {
                battle.battleTurns(card);
                updateBattleScreen();
            });

            Tooltip tooltip = new Tooltip(card.getName());
            Tooltip.install(cardImage, tooltip);

            handDisplay.getChildren().add(cardImage);
        }

        // Update summoned creatures
        summonedCreaturesDisplay.getChildren().clear();
        for (Creature creature : battle.getPlayer().getSummonedCreatures()) {
            ImageView creatureImage = new ImageView(new Image(creature.getImage()));
            creatureImage.setPreserveRatio(true);
            creatureImage.setFitWidth(CARD_WIDTH * CREATURE_SCALE);
            summonedCreaturesDisplay.getChildren().add(creatureImage);
        }

        battleLog.setText(battle.getBattleLog());

        if (battle.isVictory()) {
            wins++;
            battle.getPlayer().setMaxHealth(battle.getPlayer().getMaxHealth() + 1);
            enemy.setVisible(false);
            continueMessage.setText("You Won!\n" + "Would you like to try again?");
            endGame();
        } else if (battle.isDefeated()) {
            continueMessage.setText("You Lose!\n" + "Would you like to try again?");
            endGame();
        }
    }

    private void endGame() {
        skipButton.setVisible(false);
        handDisplay.setVisible(false);
        choiceBox.setVisible(true);
    }

    private void restartBattle() {
        battle.getPlayer().resetPlayer();
        cardDeck.add((int) (Math.random() * cardDeck.size()), chooseRandomCard());
        battle.getPlayer().givePlayerCards(cardDeck);
        battle.setNewCreature(chooseRandomCreature());
        enemy.setImage(new Image(battle.getEnemyCreature().getImage()));
        choiceBox.setVisible(false);
        enemy.setVisible(true);
        skipButton.setVisible(true);
        handDisplay.setVisible(true);
        battle.getPlayer().drawInitialCards();
        battle.startBattle();
        updateBattleScreen();
    }

    private Card chooseRandomCard() {
        Card razorSharpen = new StatusCard("Razor Sharpen", "razorsharpen.png", CardEffect.INCREASE_LOW, false);
        Card weakeningPoison = new StatusCard("Weakening Poison", "weakeningpoison.png", CardEffect.DECREASE_LOW, false);
        Card fireBall = new AttackCard("Fire Ball", "fireball.png", 10);
        Card thunderStorm = new AttackCard("Thunder Storm", "thunderstorm.png", 10);
        Card geomancy = new AttackCard("Geomancy", "geomancy.png", 10);
        Card blizzard = new AttackCard("Blizzard", "blizzard.png", 10);
        Card darkBlast = new AttackCard("Dark Blast", "darkBlast.png", 15);
        Card florarsun = new CreatureCard("Florasun", "florasun.png", 5, 10);
        Card winterWarbler = new CreatureCard("Winter Warbler", "winterwarbler.png", 12, 15);
        Card mummikat = new CreatureCard("Mummi-kat", "mummikat.png", 9, 99);
        Card volcanasaur = new CreatureCard("Volcanasaur", "volcanasaur.png", 10, 20);
        Card behemoth = new CreatureCard("Behe-Moth", "behemoth.png", 12, 34);
        Card boltmane = new CreatureCard("Boltmane", "boltmane.png", 25, 16);
        Card purifiedLily = new CreatureCard("Purified Lily", "purifiedlily.png", 8, 13);
        Card foxSage = new CreatureCard("Fox Sage", "foxsage.png", 20, 20);

        List<Card> cardList = List.of(florarsun, winterWarbler, fireBall, thunderStorm, razorSharpen, foxSage,
                mummikat, geomancy, blizzard, behemoth, volcanasaur, darkBlast, boltmane, purifiedLily, weakeningPoison);

        return cardList.get((int) (Math.random() * cardList.size()));
    }

    private Creature chooseRandomCreature() {
        Creature occulus = new Creature("Occulus", "occulus.png",
                9 + wins / 2, 100 + 10 * wins);
        Creature geist = new Creature("Geist", "geist.png",
                9 + wins / 2, 70 + 10 * wins);
        Creature desertSpirit = new Creature("Desert Spirit", "desertspirit.png",
                7 + wins / 2, 45 + 10 * wins);
        Creature terrordactyl = new Creature("Terrordactyl", "terrordactyl.png",
                8 + wins / 2, 60 + 10 * wins);
        Creature devilEnforcer = new Creature("Devil Enforcer", "devilenforcer.png",
                10 + wins / 2, 85 + 10 * wins);
        Creature rockle = new Creature("Rockle", "rockle.png",
                4 + wins / 2, 30 + 10 * wins);
        Creature cerulesect = new Creature("Cerulesect", "cerulesect.png",
                6 + wins / 2, 50 + 10 * wins);
        List<Creature> creatureList = List.of(occulus, geist, desertSpirit, terrordactyl, devilEnforcer, rockle,
                cerulesect);
        return creatureList.get((int) (Math.random() * creatureList.size()));
    }
}
