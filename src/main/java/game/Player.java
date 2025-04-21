/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
 * Date: 4/14/2025
 * Time: 2:43 PM
 *
 * Project: csci205_final_project
 * Package: game
 * Class: Player
 *
 * Description: Class that represents the player/user during a battle
 *
 * ****************************************
 */

package game;

import cards.*;
import creature.Creature;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player {

    /* Maximum amount of cards the player can have in their hand */
    private final static int MAX_CARDS_IN_HAND = 4;

    /* Player's current health */
    private int currentHealth;

    /* Boolean to check if the player is alive */
    private boolean isAlive;

    /* List of cards that represents the user's hand */
    private final List<Card> currentHand;

    /* Queue of cards that represents the player's deck */
    private final Queue<Card> playerDeck;

    /* List of all creatures the player has summoned */
    private final List<Creature> summonedCreatures;

    public Player(int maxHealth) {
        currentHealth = maxHealth;
        isAlive = true;
        currentHand = new ArrayList<>();
        playerDeck = new LinkedList<>();
        summonedCreatures = new ArrayList<>();
    }

    /**
     * Initializes the player's deck of cards with a given list of cards
     *
     * @param cardsList list of instantiated Card objects
     *
     * @author Nathan Ramkissoon
     */
    public void givePlayerCard(List<Card> cardsList) {
        playerDeck.addAll(cardsList);
    }

    /**
     * Draws a card from the player's deck and adds it to their hand
     *
     * @return boolean that represents if a card was successfully drawn
     *
     * @author Nathan Ramkissoon
     */
    public boolean drawCard() {
        if (currentHand.size() < MAX_CARDS_IN_HAND && !playerDeck.isEmpty()) {
            return currentHand.add(playerDeck.remove());
        } else {
            return false;
        }
    }

    /**
     * Removes the selected card from the player's hand
     *
     * @param usedCard Card that is selected by the player
     *
     * @author Nathan Ramkissoon
     */
    public void discardCard(Card usedCard) {
        currentHand.remove(usedCard);
    }

    /**
     * Subtracts the take damage taken from the player's current health
     *
     * @param receivedDamage integer number of damage taken
     *                       
     * @author Nathan Ramkissoon
     */
    public void takeDamage(int receivedDamage) {
        if (currentHealth - receivedDamage > 0) {
            currentHealth -= receivedDamage;
        } else {
            currentHealth = 0;
            isAlive = false;
        }
    }

    public List<Card> getCurrentHand() {
        return currentHand;
    }

    public boolean getIfAlive() {
        return isAlive;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public List<Creature> getSummonedCreatures() {
        return summonedCreatures;
    }

    public Queue<Card> getPlayerDeck() {
        return playerDeck;
    }
}
