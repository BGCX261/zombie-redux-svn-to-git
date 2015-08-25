package items;

import characters.Character;
import characters.Vampire;


/**
 * Class WoodenStake implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class WoodenStake implements Item {

    /**
     * Check if the woodenStake has been used or not (can be use only once)
     */
    private boolean isUsed;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * Normal constructor
     */
    public WoodenStake()
    {
        isUsed = false;
        type = TypeOfItem.WOODEN_STAKE;
    }

    /**
     * Using the woodenstake on a vampire will definetely kill him
     * @param c
     *          the targeted character
     * @return true if the character has been killed, false otherwise
     */
    public boolean use(Character c)
    {
        if(jammed())
            return false;

        Vampire v = (Vampire)c;
        v.reduceHealthPoints(v.getHealthPoints());
        isUsed = true;
        return true;
    }

    /**
     * Check if an item is jammed
     * @return true if the object is jammed, false otherwise
     */
    public boolean jammed()
    {
        if(isUsed())
            return true;
        else return false;
    }

    /**
     * @return the isUsed
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * @return the type
     */
    public TypeOfItem getType() {
        return type;
    }

}
