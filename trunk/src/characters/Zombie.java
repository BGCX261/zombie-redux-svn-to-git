package characters;

import java.util.Iterator;
import java.util.List;

import graphics.*;
import items.*;


/**
 * Zombie class, derives from Character.
 * @author pylaffon, Alexandre Boursier, Nolan Potier
 * @version 1
 */
public class Zombie extends Character implements Villain{

    /**
     * The number of turn a zombie is stunned if a human use a shotgun
     */
    protected int stunnedFor;

    /**
     * Check if Zombie has moved in order to make him 50% slower than the Vampire
     */
    protected boolean hasMoved;

    /**
     * The chosen health for the zombies
     */
    public static final int HP_ZOMBIES = 50;

    /**
     * Constructor of Zombie class.
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public Zombie(String name, int healthPoints, Field field, Location location) {
        super(name, healthPoints, field, location);
        hasMoved = false; 
    }

    /**
     * Second constructor of Zombie class.
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public Zombie(String name, Field field, Location location) {
        super(name, HP_ZOMBIES, field, location);
        hasMoved = false;
    }

    /**
     * Method triggered on each character at the end of each turn.
     */
    public void endOfTurn() {}

    /**
     * Each character encouter another
     * @param c
     *          the targeted character
     */
    public void act(List<Character> newCharacters) {
        if(this.getStunnedFor() != 0)
            stunnedFor--;
        else
        {
            // Move towards a source of food if found.
            Location location = getLocation();
            findHuman(location, newCharacters);
            Location newLocation = getField().freeAdjacentLocation(location);
            if(newLocation != null) {
                if(!this.hasMoved) {
                    setLocation(newLocation); 
                    this.hasMoved = true;
                }
                else {
                    this.hasMoved = false;
                } 
            }            
        } 
    }


    /**
     * Tell the zombie to look for human adjacent to its current location.
     * @param location Where in the field it is located.
     * @return Where human was found, or null if it wasn't.
     */
    public void findHuman(Location location, List<Character> newCharacters)
    {
        Field field = this.getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Character character = field.getCharacter(where);
            if(character != null && character.thisIsHero()) {
                Human target = (Human) character;
                target.beingAttacked(this);
                if(target.getHealthPoints() <= 0)
                {
                    newCharacters.add(((Human) target).turnIntoZombie());
                }
            }
        }
    }

    /**
     * Being attacked by a human
     * @param h
     *          the targeted human
     */
    public void beingAttacked(Human h)
    {
        if(h.bagContains(TypeOfItem.LIQUID_NITROGEN) && h.getItem(TypeOfItem.LIQUID_NITROGEN).use(this)) {
            h.say(this.getName() + ", I kill you with Nitrogen !");
        }
        else if(h.bagContains(TypeOfItem.SHOTGUN) && h.getItem(TypeOfItem.SHOTGUN).use(this)){
            h.say(this.getName() + ", I am stunning you with Shotgun !");
        }
        else if(h.bagContains(TypeOfItem.PISTOL) && h.getItem(TypeOfItem.PISTOL).use(this)) {
            h.say(this.getName() + ", Look at my pistol !");
        }

    }

    /**
     * @return the stunnedFor
     */
    public int getStunnedFor() {
        return stunnedFor;
    }

    /**
     * @param stunnedFor the stunnedFor to set
     */
    public void setStunnedFor(int stunnedFor) {
        this.stunnedFor = stunnedFor;
    }


}
