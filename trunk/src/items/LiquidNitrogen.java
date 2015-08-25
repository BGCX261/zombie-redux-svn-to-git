package items;

import characters.Character;
import characters.Zombie;


/**
 * Class liquidNitrogen implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class LiquidNitrogen implements Item {

    /**
     * The leaving gauge for the liquid
     */
    private float percentage_left;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * Normal constructor (100% of liquid)
     */
    public LiquidNitrogen()
    {
        this.percentage_left = 100;
        type = TypeOfItem.LIQUID_NITROGEN;
    }

    /**
     * Using the liquidNitrogen on a zombie will instantly kill him
     * @param c
     *          the targeted character
     * @return true if the character has been killed, false otherwise
     */
    public boolean use(Character c)
    {
        if(jammed())
            return false;

        Zombie z = (Zombie)c;
        // Reduce the liquid between 0 and 30%
        double ammoUsed = (double)(Math.random() * 30);
        z.reduceHealthPoints(z.getHealthPoints());
        percentage_left -= ammoUsed;
        return true;
    }

    /**
     * Check if an item is jammed
     * @return true if the object is jammed, false otherwise
     */
    public boolean jammed()
    {
        if(this.getPercentage_left() <= 0.00)
            return true;
        else return false;
    }

    /**
     * @return the percentage_left
     */
    public double getPercentage_left() {
        return percentage_left;
    }

    /**
     * @return the type
     */
    public TypeOfItem getType() {
        return type;
    }

}
