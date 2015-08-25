package characters;

import java.util.List;

import graphics.Field;
import graphics.Location;

/**
 * Parent Character class
 * @author pylaffon, Alexandre Boursier, Nolan Potier
 * @version 1
 *
 */

public abstract class Character {

    /**
     * the name of the character
     */
    protected String name; 

    /**
     * The health of the character
     */
    protected int healthPoints; 

    /**
     * The character's field.
     */
    private Field field;

    /**
     * The character's position in the field.
     */
    private Location location;

    /**
     * Constructor of Character class.
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public Character(String name, int healthPoints, Field field, Location locations) {
        this.name = name;
        this.healthPoints = healthPoints;
        this.field = field;
        this.setLocation(locations);
    }

    /**
     * @return the name of the character
     */
    public String getName() {
        return name;
    }

    /**
     * @return the current healthpoints of the character
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Return the character's location.
     * @return The character's location.
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Return the character's field.
     * @return The character's field.
     */
    public Field getField()
    {
        return field;
    }

    /**
     * Place the character at the new location in the given field.
     * @param newLocation The character's new location.
     */
    public void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.placeCharacter(this, newLocation);
    }

    /**
     * Check whether the character is alive or not.
     * @return true if the character is still alive.
     */
    public boolean isAlive()
    {
        return healthPoints > 0;
    }

    /**
     * Indicate that the character is no longer alive.
     * It is removed from the field.
     */
    public void setDead()
    {
        this.healthPoints = 0;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Decrease the number of HP by a certain amount. HP cannot go below 0.
     * @param reduction number of HP to reduce
     */
    public void reduceHealthPoints(int reduction) {
        healthPoints = healthPoints - reduction;
        if (healthPoints < 0) {
            healthPoints = 0;
        }
    }

    /**
     * Output a character's saying to the screen
     * @param str what the character says
     */
    public void say(String str) {
        System.out.println(" * " + name.toUpperCase() + "(" + this.getHealthPoints() + "HP) \t\t: \t\t" + str);
    }

    /**
     * Executing the end of turn of a character
     */
    public abstract void endOfTurn();

    /**
     * Each character encouter another
     * @param c
     *          the targeted character
     */
    abstract public void act(List<Character> newCharacters);

    /**
     * Method which defines if the Character is an instance of Human
     * @return
     *          true if this is an instance of Human, false otherwise (villains)
     */
    public boolean thisIsHero(){return false;}

}
