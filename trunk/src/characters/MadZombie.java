package characters;

import java.util.Iterator;
import java.util.List;

import graphics.*;


/**
 * MadZombie class, derives from Zombie
 * @author pylaffon, Alexandre Boursier , Nolan Potier
 * @version 1
 *
 */
public class MadZombie extends Zombie {

    /**
     * first constructor of Zombie class.
     * @param name name of the character
     * @param healthPoints initial HP
     */
    public MadZombie(String name, int healthPoints, Field field, Location location) {
        super(name, healthPoints, field, location);
    }

    /**
     * Second constructor of Zombie class.
     * @param name name of the character
     */
    public MadZombie(String name, Field field, Location location) {
        super(name, field, location);
    }

    /**
     * Tell the Madzombie to look for human adjacent to its current location.
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
}