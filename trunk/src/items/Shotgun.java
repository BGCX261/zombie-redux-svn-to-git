package items;

import characters.Character;
import characters.Zombie;


/**
 * Class Shotgun implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class Shotgun implements Item{

    /**
     * The leaving bullets
     */
    private static int bullets = 12;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * The maximum number of turns a zombie can be stuned
     */
    private static final int MAX_STUN = 5;

    /**
     * Normal constructor 
     * @param nb
     *          the number of bullets in the weapon
     */
    public Shotgun()
    {
        type = TypeOfItem.SHOTGUN;
    }

    /**
     * Using the liquidNitrogen on a zombie will stun him for a number of turns
     * @param c
     *          the targeted character
     * @return true if the character has been stuned, false otherwise
     */
    public boolean use(Character c)
    {
        if(jammed())
            return false;

        Zombie z = (Zombie)c;
        int turns = (int)(Math.random() * getMaxStun());
        z.setStunnedFor(turns);
        bullets--;
        return true;
    }

    /**
     * @return the bullets
     */
    public int getBullets() {
        return bullets;
    }

    /**
     * Check if an item is jammed
     * @return true if the object is jammed, false otherwise
     */
    public boolean jammed()
    {
        if(this.getBullets() == 0)
            return true;
        else return false;
    }

    /**
     * @return the type
     */
    public TypeOfItem getType() {
        return type;
    }

    /**
     * @return the maxStun
     */
    public static int getMaxStun() {
        return MAX_STUN;
    }

}
