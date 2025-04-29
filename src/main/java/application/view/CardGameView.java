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
import javafx.scene.control.Button;
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
    private Button skipButton;
    private ImageView enemy;
    private HBox handDisplay;
    private VBox summonedCreaturesDisplay;
    private final BattleManager battle;

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

        for (int i = 0; i < battle.getPlayer().getMaxCardsInHand(); i++) {
            battle.getPlayer().drawCard();
        }

        // Enemy setup
        enemy = new ImageView(battle.getEnemyCreature().getImage());
        enemy.relocate(ENEMY_LOCATION, 0);
        root.getChildren().add(enemy);

        // Health labels
        playerHealthLabel = new Label("Player Health: " + battle.getPlayer().getCurrentHealth());
        playerHealthLabel.relocate(50, 20);
        root.getChildren().add(playerHealthLabel);

        enemyHealthLabel = new Label("Enemy Health: " + battle.getEnemyCreature().getHealth());
        enemyHealthLabel.relocate(800, 20);
        root.getChildren().add(enemyHealthLabel);

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
        summonedCreaturesDisplay = new VBox(CARD_SPACE);
        summonedCreaturesDisplay.relocate(CREATURES_LOCATION, FIRST_CREATURE_LOCATION);
        root.getChildren().add(summonedCreaturesDisplay);
    }

    /**
     * Updates the battle screen with the latest information.
     *
     * @author Muhammad Ahmed & Nathan Ramkissoon
     */
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
        for (Creature creature : battle.getPlayer().getSummonedCreatures()) {
            ImageView creatureImage = new ImageView(new Image(creature.getImage()));
            creatureImage.setPreserveRatio(true);
            creatureImage.setFitWidth(CARD_WIDTH * CREATURE_SCALE);
            summonedCreaturesDisplay.getChildren().add(creatureImage);
        }

        if (battle.isVictory()) {
            root.getChildren().remove(enemy);
            root.getChildren().remove(skipButton);
        } else if (battle.isDefeat()) {
            root.getChildren().remove(skipButton);
        }
    }
}
