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

import cards.Card;
import creature.Creature;
import game.BattleManager;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class CardGameView {
    private Pane root;
    private Label playerHealthLabel;
    private Label enemyHealthLabel;
    private ImageView enemy;
    private HBox handDisplay;
    private VBox summonedCreaturesDisplay;
    private BattleManager battle;

    private int handLocation = 550;
    private int cardWidth = 120;
    private int cardSpace = 30;
    private int firstHandCardLocation = 255;
    private int nextCardLocation = cardWidth + cardSpace;
    private int creatureCardLocationX = 930;
    private int firstCreatureCardLocationY = 0;
    private double creatureScale = 0.75;
    private int enemyLocation = 290;

    public CardGameView(BattleManager battle) {
        this.battle = battle;
        initializeView();
    }

    public Pane getRoot() {
        return root;
    }

    public void initializeView() {
        root = new Pane();

        for (int i = 0; i < battle.getPlayer().getMaxCardsInHand(); i++) {
            battle.getPlayer().drawCard();
        }

        // Enemy setup
        enemy = new ImageView(battle.getEnemyCreature().getImage());
        enemy.relocate(enemyLocation, 0);
        root.getChildren().add(enemy);

        // Health labels
        playerHealthLabel = new Label("Player Health: " + battle.getPlayer().getCurrentHealth());
        playerHealthLabel.relocate(50, 20);
        root.getChildren().add(playerHealthLabel);

        enemyHealthLabel = new Label("Enemy Health: " + battle.getEnemyCreature().getHealth());
        enemyHealthLabel.relocate(800, 20);
        root.getChildren().add(enemyHealthLabel);

        // Cards in hand
        handDisplay = new HBox(cardSpace);
        handDisplay.relocate(firstHandCardLocation, handLocation);
        root.getChildren().add(handDisplay);

        for (int i = 0; i < battle.getPlayer().getCurrentHand().size(); i++) {
            Card card = battle.getPlayer().getCurrentHand().get(i);
            ImageView cardImage = new ImageView(new Image(card.getImage()));

            cardImage.setOnMouseClicked(event -> {
                battle.battleTurns(card);
                updateBattleScreen();
            });

            handDisplay.getChildren().add(cardImage);
        }

        // Summoned creatures
        summonedCreaturesDisplay = new VBox(cardSpace);
        summonedCreaturesDisplay.relocate(creatureCardLocationX, firstCreatureCardLocationY);
        root.getChildren().add(summonedCreaturesDisplay);
    }

    private void updateBattleScreen() {
        // Update health labels
        playerHealthLabel.setText("Player Health: " + battle.getPlayer().getCurrentHealth());
        enemyHealthLabel.setText("Enemy Health: " + battle.getEnemyCreature().getHealth());

        // Update cards
        handDisplay.getChildren().clear();
        for (Card card : battle.getPlayer().getCurrentHand()) {
            ImageView cardImage = new ImageView(new Image(card.getImage()));

            cardImage.setOnMouseClicked(event -> {
                battle.battleTurns(card);
                updateBattleScreen();
            });

            handDisplay.getChildren().add(cardImage);
        }

        // Update summoned creatures
        summonedCreaturesDisplay.getChildren().clear();
        for (int i = 0; i < battle.getPlayer().getSummonedCreatures().size(); i++) {
            Creature creature = battle.getPlayer().getSummonedCreatures().get(i);
            ImageView cardImage = new ImageView(new Image(creature.getImage()));
            cardImage.setPreserveRatio(true);
            cardImage.setFitWidth(cardWidth * creatureScale);
            summonedCreaturesDisplay.getChildren().add(cardImage);
        }
    }
}
