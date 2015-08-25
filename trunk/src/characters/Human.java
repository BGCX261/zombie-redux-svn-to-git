package characters;

import graphics.*;
import items.*;

import java.util.*;


/**
 * Human class, derives from Character
 * @author pylaffon, Alexandre Boursier , Nolan Potier
 * @version 1
 *
 */

public class Human extends Character  {

    /**
     * false, until a vampire bites this human
     */
    private boolean hasBeenBitten;  

    /**
     * a list of the current items the character is wearing
     */
    private List<Item> bag;

    /**
     * The chosen health for the humans
     */
    public static final int HP_HUMANS = 100;

    /**
     * The likelihood of a human breeding.
     */
    private static final double BREEDING_PROBABILITY = 0.10;

    /**
     *  The maximum number of births.
     */
    private static final int MAX_LITTER_SIZE = 4;

    /**
     * the human will lose health if he's too hungry
     */
    private int turnsSinceLastMeal; 

    /**
     *  A shared random number generator to control breeding.
     */
    private static final Random rand = Randomizer.getRandom();

    /**
     * Constructor of Human class.
     * At the beginning of the game, humans just had dinner, and have not been bitten yet.
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public Human(String name, Field field, Location location) {
        super(name, HP_HUMANS, field, location);
        hasBeenBitten = false;
        bag = new ArrayList<Item>();
    }

    /**
     * @return the bag
     */
    public List<Item> getBag() {
        return bag;
    }

    /**
     * Take an item in a particular location
     * @param i
     *          the targeted item
     */
    public void take(Item i)
    {
        bag.add(i);
    }

    /**
     * Check if the items are jammed or not <BR>
     * Heal if possible <BR>
     * Eat if possible
     * 
     */
    public void checkEquipement()
    {
        if(bagContains(TypeOfItem.MEAL))
        {
            this.getItem(TypeOfItem.MEAL).use(this);
        } 
        if(bagContains(TypeOfItem.HEALTH_PACK))
        {
            this.getItem(TypeOfItem.HEALTH_PACK).use(this);
        } 
        for(Iterator<Item> it = bag.iterator(); it.hasNext();)
        {
            Item i = it.next();
            if(i.jammed())
                it.remove();
        }
    }

    /**
     * Get a given Item
     * @param t
     *          the targeted item
     * @return the item if found, null otherwise
     */
    public Item getItem(TypeOfItem t)
    {
        for(Item i : bag)
        {
            if(i.getType().equals(t))
                return i;
        }
        return null;
    }

    /**
     * Check if the bag contains an element
     * @param t
     *          the type of item to look for
     * @return true if the item is in, false otherwise
     */
    public boolean bagContains(TypeOfItem t)
    {
        if(getItem(t) != null)
        {
            return true;
        }
        return false;
    }

    /**
     * Using an item on a character
     * @param t
     *          the targeted item
     * @param c
     *          the targeted character
     */
    public boolean use(Item t,Character c)
    {
        boolean result = false;
        if(bagContains(t.getType()))
            result = this.getItem(t.getType()).use(c);
        return result;
    }


    /**
     * @return true if the human has been bitten, false otherwise
     */
    public boolean getHasBeenBitten() {
        return hasBeenBitten;
    }

    /**
     * Alter the attribute hasBeenBitten
     * @param hasBeenBitten : true if the human has to be bitten, false otherwise
     */
    public void setHasBeenBitten(boolean hasBeenBitten) {
        this.hasBeenBitten = hasBeenBitten;
    }

    /**
     * @return the turnsSinceLastMeal
     */
    public int getTurnsSinceLastMeal() {
        return turnsSinceLastMeal;
    }

    /**
     * @param turnsSinceLastMeal the turnsSinceLastMeal to set
     */
    public void setTurnsSinceLastMeal(int turnsSinceLastMeal) {
        this.turnsSinceLastMeal = turnsSinceLastMeal;
    }

    /**
     * Method triggered on each character at the end of each turn.
     */
    public void endOfTurn() {
        // Increment the number of turns since the last time the human ate
        turnsSinceLastMeal++;
        // If the human is too hungry, he will lose health...
        if (turnsSinceLastMeal > 3) {
            healthPoints -= 5;
        }
    }

    /**
     * Transform this human who has been bitten, into a blood-thirsty vampire.
     * @return a new object of class Vampire, with the same name and healthpoints
     * as this human; the new vampire is immediately thirsty
     */
    public Vampire turnIntoVampire() {
        int newHealth = healthPoints;
        if (newHealth > Vampire.HP_VAMPIRES)
            newHealth = Vampire.HP_VAMPIRES;
        Field field = this.getField();
        Location location = this.getLocation();
        this.setDead();
        Vampire v = new Vampire(name,newHealth, field, location);        
        v.setIsThirsty(true);
        return v;
    }

    /**
     * Transform this human who has no more healthpoints left
     * @return a new object of class Zombie, with the same name and 
     * the chosen healthpoints for the zombie
     */
    public Zombie turnIntoZombie() {
        Field field = this.getField();
        Location location = this.getLocation();
        this.setDead();
        Zombie z = new Zombie(name, field, location);
        return z;
    }

    /**
     * Each character encouter another
     * @param c
     *          the targeted character
     */
    public void act(List<Character> newCharacters) {
        if(this.getHasBeenBitten()) {
            newCharacters.add(this.turnIntoVampire());
        }
        else
        {
            Location newLocation = findItems(this.getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                this.giveBirth(newCharacters);
                this.checkEquipement();
                findEnnemies(this.getLocation());
                // Try to move into a free location.
                newLocation = this.getField().freeAdjacentLocation(getLocation());
                if(newLocation != null) {
                    setLocation(newLocation);
                }
            }
        }
    }

    /**
     * Tell the human to look for zombie or vampire adjacent to its current location.
     * @param location Where in the field it is located.
     */
    public void findEnnemies(Location location)
    {
        Field field = this.getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Character ennemy = field.getCharacter(where);
            if(ennemy != null && !ennemy.thisIsHero() ) {
                    ((Villain)ennemy).beingAttacked(this);
            }
        }
    }

    /**
     * Being attacked by a vampire
     * @param v
     *          the targeted vampire
     */
    public void beingAttacked(Vampire v)
    {
        this.reduceHealthPoints(10);     
    }

    /**
     * Being attacked by a zombie
     * @param z
     *          the targeted zombie
     */
    public void beingAttacked(Zombie z)
    {
        this.reduceHealthPoints(25);
    }  

    /**
     * Being attacked by a mad zombie
     * @param m
     *          the targeted mad zombie
     */
    public void beingAttacked(MadZombie m)
    {
        this.reduceHealthPoints(40);
    }

    /**
     * Check whether or not this human is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newHumans A list to add newly born Humans to.
     */
    private void giveBirth(List<Character> newHumans)
    {
        // New Humans are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Human young = new Human("Human born " + b, field, loc);
            young.take(new Pistol());
            newHumans.add(young);
        }
    }

    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    private int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= BREEDING_PROBABILITY) {
            births = rand.nextInt(MAX_LITTER_SIZE) + 1;
        }
        return births;
    }

    /**
     * A human can breed if he has more 50% of his initial HealthPoints
     * @return true if the human can breed, false otherwise.
     */
    private boolean canBreed()
    {
        return healthPoints >= (HP_HUMANS/10);
    }


    @Override
    /**
     * Method which defines if the Character is an instance of Human
     * @return
     *          true if this is an instance of Human, false otherwise (villains)
     */
    public boolean thisIsHero(){return true;}


    /**
     * Tell the human to look for item adjacent to its current location.
     * @param location Where in the field it is located.
     */
    public Location findItems(Location location)
    {
        Field field = this.getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Item item = field.getItem(where);
            if(item != null) {
                this.take(item);
                field.clear(where);
                return where;
            }
        }
        return null;
    }

}
