/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Muhammad Ahmed
 * Date: 4/14/2025
 * Time: 2:43 PM
 *
 * Project: csci205_final_project
 * Package: creature
 * Class: Creature
 *
 * Description:
 *
 * ****************************************
 */

package creature;

/**
 * Represents a creature that can be summoned and used in battle.
 */
public class Creature {
    private String name;
    private int damage;
    private int currentHealth;
    private boolean isAlive;

    private final int MAX_HEALTH;

    public Creature(String name, int damage, int maxHealth) {
        this.name = name;
        this.damage = damage;
        this.currentHealth = maxHealth;
        this.isAlive = true;
        MAX_HEALTH = maxHealth;
    }

    public int getDamage() {
        return damage;
    }

    public void increaseDamage(int value) {
        damage += value;
    }

    public void decreaseDamage(int value) {
        damage = Math.max(0, damage - value);
    }

    public int getHealth() {
        return currentHealth;
    }

    public void heal(int amount) {
        currentHealth += amount;
        if (currentHealth > MAX_HEALTH) {
            currentHealth = MAX_HEALTH;
        }
    }

    public void takeDamage(int dmg) {
        currentHealth -= dmg;
        if (currentHealth <= 0) {
            currentHealth = 0;
            isAlive = false;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getName() {
        return name;
    }
}
