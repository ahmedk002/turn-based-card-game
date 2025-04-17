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

import cards.*;
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
    private final List<Creature> summonedCreatures;

    public Player(int maxHealth) {
        currentHealth = maxHealth;
        isAlive = true;
        currentHand = new ArrayList<>();
        playerDeck = new LinkedList<>();
        summonedCreatures = new ArrayList<>();
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

    public boolean getIfAlive() {
        return isAlive;
    }

    public Queue<Card> getPlayerDeck() {
        return playerDeck;
    }

    public void takeDamage(int recievedDamage) {
        if (currentHealth - recievedDamage > 0) {
            currentHealth -= recievedDamage;
        } else {
            currentHealth = 0;
            isAlive = false;
        }
    }

    public int useCard(Card usedCard) {
        int damageNumber = 0;
        switch (usedCard.getType()) {
            case CardType.STATUS:
                StatusCard usedStatusCard = (StatusCard) usedCard;
                usedStatusCard.useStatusCard(summonedCreatures);
                break;
            case CardType.ATTACK:
                AttackCard usedAttackCard = (AttackCard) usedCard;
                damageNumber = usedAttackCard.useAttackCard();
                break;
            case CardType.CREATURE:
                CreatureCard usedCreatureCard = (CreatureCard) usedCard;
                Creature creature = usedCreatureCard.useCreatureCard();
                summonedCreatures.add(creature);
                break;
            default:
                System.out.println("Card has invalid type");
        }
        discardCard(usedCard);
        return damageNumber;
    }
}
