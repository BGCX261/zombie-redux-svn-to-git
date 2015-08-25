package items;

import characters.Character;

/**
 * Class HealthPack implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class HealthPack implements Item {

    /**
     * The number of healthpoints the healthpack can give
     */
    private static final int HP = 30;

    /**
     * Check if the pack is used or not
     */
    private boolean used;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * Normal constructor 
     */
    public HealthPack()
    {
        this.used = false;
        type = TypeOfItem.HEALTH_PACK;
    }

    /**
     * Using the healthpack will heal a human
     * @param c
     *          the targeted character
     * @return true if the character has been healed, false otherwise
     */
    public boolean use(Character c)
    {
        if(jammed())
            return false;

        c.reduceHealthPoints(-HP);
        used = true;
        return true;
    }

    /**
     * Check if an item is jammed
     * @return true if the object is jammed, false otherwise
     */
    public boolean jammed()
    {
        if(used)
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
     * @return the hp
     */
    public static int getHp() {
        return HP;
    }

}
