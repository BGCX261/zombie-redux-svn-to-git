package characters;

import java.util.Iterator;
import java.util.List;

import graphics.*;
import items.*;

/**
 * Vampire class, derives from Character.
 * @author pylaffon, Alexandre Boursier , Nolan Potier
 * @version 1
 */
public class Vampire extends Character implements Villain{

    /**
     * First of all, a vampire isn't thirsty, so he can't bite humans
     */
    private boolean isThirsty;

    /**
     * The chosen health for the vampires
     */
    public static final int HP_VAMPIRES = 20;

    /**
     * Constructor of Vampire class. <BR>
     * First of all, a vampire isn't thirsty, so he can't bite humans
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public Vampire(String name, int healthPoints, Field field, Location location) {
        super(name,healthPoints, field, location);
        isThirsty = false;
    }

    /**
     * Second constructor of Vampire class.
     * @param name name of the character
     */
    public Vampire(String name, Field field, Location location) {
        super(name, HP_VAMPIRES, field, location);
        isThirsty = false;
    }

    /**
     * @return true if the vampire is thirsty, false otherwise
     */
    public boolean getIsThirsty() {
        return isThirsty;
    }

    /**
     * Set the attribute if a vampire is thirsty
     * @param isThirsty
     *          false or true
     */
    public void setIsThirsty(boolean isThirsty) {
        this.isThirsty = isThirsty;
    }

    /**
     * Method triggered on each character at the end of each turn.
     */
    public void endOfTurn() {
        // The vampire has 50% chance of becoming thirsty, if he is not already
        if (isThirsty || Randomizer.GenerateRandomBoolean()) {
            isThirsty = true;
        }
    }

    /**
     * Method called when a vampire decides to bite a human
     * @param h Human who gets bitten by this vampire
     */
    public void bite(Human h) {
        // The human has no way to escape. He gets bitten.
        h.setHasBeenBitten(true);
        // Vampire is not thirsty anymore
        isThirsty = false;
    }

    /**
     * Each character encouter another
     * @param c
     *          the targeted character
     */
    public void act(List<Character> newCharacters) {
        // Move towards a source of food if found.
        Location location = getLocation();
        Location newLocation = findHuman(location);
        if(newLocation == null) { 
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(location);
            if(newLocation != null) {
                setLocation(newLocation);
            }
        }
        // See if it was possible to move.
        else if(newLocation != null) {
            setLocation(newLocation); 
        }
    }

    /**
     * Tell the vampire to look for human adjacent to its current location.
     * @param location Where in the field it is located.
     * @return Where human was found, or null if it wasn't.
     */
    public Location findHuman(Location location)
    {
        Field field = this.getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Character character = field.getCharacter(where);
            if(character != null && character.thisIsHero()) {
                Human target = (Human) character;
                if(isThirsty && !target.getHasBeenBitten())
                    this.bite(target);
                else
                    target.beingAttacked(this);
                if(!target.isAlive())
                {
                    target.setDead();
                    return where;
                }    
                return null;
            }
        }
        return null;
    }

    /**
     * Being attacked by a human
     * @param h
     *          the targeted human
     */
    public void beingAttacked(Human h)
    {
        //System.out.println(h.bagContains(TypeOfItem.PISTOL));
        if(h.bagContains(TypeOfItem.WOODEN_STAKE) && h.getItem(TypeOfItem.WOODEN_STAKE).use(this)) {
            h.say(this.getName() + ", I kill you with my Wooden Stake !");
        }
        else if(h.bagContains(TypeOfItem.PISTOL) && h.getItem(TypeOfItem.PISTOL).use(this)) {
            h.say(this.getName() + ", Look at my pistol !");
        }
    }

}
