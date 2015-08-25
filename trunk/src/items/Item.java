package items;

import characters.Character;


/**
 * Interface item : shows the methods which have to be implemented for each item
 * @author Alexandre Boursier, Nolan Potier
 * @version 1
 *
 */

public interface Item {

    /**
     * Using an object (for a given number of times)
     * @param c
     *          the targeted character receiving the object(attack or health)
     * @return true if the object has been used, false otherwise
     */
    boolean use(Character c);

    /**
     * Check if an item is jammed
     * @return true if the object is jammed, false otherwise
     */
    boolean jammed();

    /**
     * Get the type of the item
     * @return
     */
    public TypeOfItem getType();
}
