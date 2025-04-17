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
 * Description:
 *
 * ****************************************
 */

package game;

import cards.Card;
import creature.Creature;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Player {
    private final static int MAX_CARDS_IN_HAND = 4;
    private int currentHealth;
    private boolean isAlive;
    private final List<Card> currentHand;
    private final Queue<Card> playerDeck;
    private final List<Creature> summonedCreatres;

    public Player(int maxHealth) {
        currentHealth = maxHealth;
        isAlive = true;
        currentHand = new ArrayList<>();
        playerDeck = new LinkedList<>();
        summonedCreatres = new ArrayList<>();
    }

    public void givePlayerCard(List<Card> cardsList) {
        playerDeck.addAll(cardsList);
    }

    public void discardCard(Card usedCard) {
        currentHand.remove(usedCard);
    }

    public boolean drawCard() {
        if (currentHand.size() < MAX_CARDS_IN_HAND && !playerDeck.isEmpty()) {
            return currentHand.add(playerDeck.remove());
        } else {
            return false;
        }
    }

    public boolean checkIfAlive() {
        return isAlive;
    }

    public Queue<Card> getPlayerDeck() {
        return playerDeck;
    }

    public int useCard(Card usedCard) {
        int damageNumber = 0;
        switch (usedCard.getType()) {
            case Card.STATUS:
                usedCard.useStatusCard(summonedCreatres);
                break;
            case Card.ATTACK:
                damageNumber = usedCard.useAttackCard();
                break;
            case Card.CREATURE:
                Creature creature = usedCard.useCreatureCard();
                summonedCreatres.add(creature);
                break;
            default:
                System.out.println("Card has invalid type");
        }
        discardCard(usedCard);
        return damageNumber;
    }
}
