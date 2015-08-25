package characters;

/**
 * Interface Villain : shows the methods which have to be implemented for each villain
 * @author Alexandre Boursier, Nolan Potier
 * @version 1
 *
 */

public interface Villain {

    /**
     * Each villain gets the possibility to be attacked by a human
     * @param h
     */
    void beingAttacked(Human h);

}
