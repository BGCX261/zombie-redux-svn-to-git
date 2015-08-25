package items;

import characters.Character;
import characters.Human;


/**
 * Class Meal implementing the interface Item
 * @author Alexandre Boursier, Nolan Potier
 *
 */

public class Meal implements Item {

    /**
     * The leaving gauge for the liquid
     */
    private boolean used;

    /** 
     * The type of item it is
     */
    private TypeOfItem type;

    /**
     * Normal constructor (100% of liquid)
     */
    public Meal()
    {
        this.used = false;
        type = TypeOfItem.MEAL;
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

        ((Human)c).setTurnsSinceLastMeal(0);
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

}
