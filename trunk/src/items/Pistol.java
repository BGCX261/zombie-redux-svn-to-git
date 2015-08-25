package items;

import characters.Character;

/**
 * Class HealthPack implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class Pistol implements Item {

    /**
     * The leaving bullets
     */
    private static int bullets = 50;

    /**
     * The number of hp a character will lose
     */
    private static final int ATTACK_POWER = 10;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * Normal constructor 
     */
    public Pistol()
    {
        type = TypeOfItem.PISTOL;
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
        c.reduceHealthPoints(ATTACK_POWER);
        bullets--;
        return true;
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
     * @return the bullets
     */
    public int getBullets() {
        return bullets;
    }

    /**
     * @return the type
     */
    public TypeOfItem getType() {
        return type;
    }

    /**
     * @return the attackPower
     */
    public static int getAttackPower() {
        return ATTACK_POWER;
    }

}
