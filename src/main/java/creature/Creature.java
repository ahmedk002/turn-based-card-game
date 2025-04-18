/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Nathan Ramkissoon
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


public class Creature {
    private String name;
    private int damage;
    private int currentHealth;
    private boolean isAlive;

    public Creature(String name, int damage, int maxHealth) {
        this.name = name;
        this.damage = damage;
        this.currentHealth = maxHealth;
        this.isAlive = true;
    }

    public int getDamage() {
        return damage;
    }

    public void takeDamage(int dmg) {
        this.currentHealth -= dmg;
        if (this.currentHealth <= 0) {
            this.isAlive = false;
            this.currentHealth = 0;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }
}
