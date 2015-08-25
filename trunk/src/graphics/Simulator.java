package graphics;


import items.HealthPack;
import items.Item;
import items.LiquidNitrogen;
import items.Meal;
import items.Pistol;
import items.Shotgun;
import items.WoodenStake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import characters.Character;
import characters.Human;
import characters.MadZombie;
import characters.Vampire;
import characters.Zombie;

/**
 * Simulator for Midterm Zombiegame.
 * @author pylaffon
 *
 */
public class Simulator {

    // Constants representing configuration information for the simulation.

    /**
     *  The default width for the grid.
     */
    private static final int DEFAULT_WIDTH = 50;

    /**
     *  The default depth of the grid.
     */
    private static final int DEFAULT_DEPTH = 50;

    /**
     *  A graphical view of the simulation.
     */
    private SimulatorView view;

    /**
     * The current wave the humans are facing off
     */
    private int numberOfTurns = 1;

    /**
     *  List of characters currently in the game
     */
    private List<Character> characterList;

    /**
     *  The probability that a human will be created in any given grid position.
     */
    private static final double HUMAN_CREATION_PROBABILITY = 0.01;

    /**
     *  The probability that a vampire will be created in any given grid position.
     */
    private static final double VAMPIRE_CREATION_PROBABILITY = 0.006; 

    /**
     *  The probability that a zombie will be created in any given grid position.
     */
    private static final double ZOMBIE_CREATION_PROBABILITY = 0.01;  

    /**
     *  The probability that a mad zombie will be created in any given grid position.
     */
    private static final double MADZOMBIE_CREATION_PROBABILITY = 0.01;  

    /**
     * The likelihood of a human recieving an item.
     */
    private static final double HUMAN_RECEIVING_ITEM_PROBABILITY = 0.33;

    /**
     *  The current state of the field.
     */
    private Field field;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            //System.out.println("The dimensions must be greater than zero.");
            //System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }

        characterList = new ArrayList<Character>();
        field = new Field(depth, width);

        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        view.setColor(Human.class, Color.blue);
        view.setColor(Vampire.class, Color.red);
        view.setColor(Zombie.class, Color.black);
        view.setColor(MadZombie.class, Color.gray);
        view.setColor(HealthPack.class, Color.green);
        view.setColor(Shotgun.class, Color.orange);
        view.setColor(LiquidNitrogen.class, Color.yellow);
        view.setColor(Meal.class, Color.MAGENTA);
        view.setColor(WoodenStake.class, Color.PINK);

        // Setup a valid starting point.
        reset();
    }

    /**
     * Initialize game.
     */
    public void reset() {
        numberOfTurns = 0;
        characterList.clear();
        populate();
        getPistols();
        view.showStatus(numberOfTurns, field);
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * e.g. 500 steps.
     * @throws InterruptedException 
     */
    public void runLongSimulation() throws InterruptedException
    {
        simulate(500);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     * @throws InterruptedException 
     */
    public void simulate(int numSteps) throws InterruptedException
    {
        for(int step = 1; step <= numSteps && view.isViable(field) 
                && this.nbHumansAlive() != characterList.size() &&
                this.nbHumansAlive() > 0; step++) {
            Thread.sleep(100);
            simulateOneStep();
        }
    }

    /**
     * Perform all game logic for next turn.
     * @throws InterruptedException 
     */
    public void simulateOneStep() throws InterruptedException {

        numberOfTurns++;
        
        //Thread.sleep(1000);

        System.out.println("\n\n*****************************");
        System.out.println("*         WAVE N°" + numberOfTurns + "          *");
        System.out.println("*****************************\n");

        System.out.println(" * --------- Santa-Claus is offering you some items !!! -------- *");        
        giveItems();
        view.showStatus(numberOfTurns, field);  
        
        //Thread.sleep(2000);

        System.out.println(" * -------ACTIONS-------");
        act();

        System.out.println("\n * -----END OF TURN-----");
        endOfTurn();

        view.showStatus(numberOfTurns, field);

    }

    /**
     * Randomly populate the field with foxes and rabbits.
     */
    public void populate()
    {
        Random rand = Randomizer.getRandom();
        field.clear();
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {
                if(rand.nextDouble() <= HUMAN_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Character human = new Human("Human " +row + "/" +col, field, location);
                    characterList.add(human);
                }
                else if(rand.nextDouble() <= VAMPIRE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Character vampire = new Vampire("Vampire " +row + "/" +col, field, location);
                    characterList.add(vampire);
                }
                else if(rand.nextDouble() <= ZOMBIE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Character zombie = new Zombie("Zombie " +row + "/" +col, field, location);
                    characterList.add(zombie);
                }
                else if(rand.nextDouble() <= MADZOMBIE_CREATION_PROBABILITY) {
                    Location location = new Location(row, col);
                    Character madzombie = new MadZombie("Madzombie " +row + "/" +col, field, location);
                    characterList.add(madzombie);
                }
                // else leave the location empty.
            }
        }
    }

    /**
     * Give to each human a pistol
     */
    public void getPistols()
    {
        for(Character c : characterList)
        {
            if(c.thisIsHero())
            {
                ((Human) c).take(new Pistol());
            }
        }
    }

    /**
     * All characters encounter the next character in the list
     */
    public void act()
    {
        // Provide space for newborn characters.
        List<Character> newCharacters = new ArrayList<Character>(); 
        for(Iterator<Character> it = characterList.iterator(); it.hasNext(); ) {
            Character character = it.next();
            if(checkForRemoving(character)) {
                it.remove();
            }
            else {                
                character.act(newCharacters);
            }
        }

        // Add the newly born humans in the list
        characterList.addAll(newCharacters);
    }

    /**
     * Dead characters are removed from the character list
     */
    public boolean checkForRemoving(Character c)
    {
        if (!c.isAlive()) {
            c.setDead();
            return true;
        }
        return false;
    }

    /**
     * Let each character execution his endOfTurn's action
     */
    public void endOfTurn()
    {
        // Perform end-of-turn actions for all characters (question 4)
        for (int i = 0; i < characterList.size(); ++i) {
            Character c = characterList.get(i);
            c.endOfTurn();
        }
    }

    /**
     * @return the number of human characters currently in the game
     */
    public int nbHumansAlive() {
        // Need to iterate through the list of characters
        // and count the number of humans
        int nbHumans = 0;
        for (Character character : characterList) {
            if (character.thisIsHero()) {
                nbHumans++;
            }
        }
        return nbHumans;
    }

    /**
     * @return the numberOfTurns
     */
    public int getNumberOfTurns() {
        return numberOfTurns;
    }

    public void giveItems() {
        Random rand = Randomizer.getRandom();

        for(int row=0 ; row<field.getDepth(); row++) {
            for(int col=0 ; col<field.getWidth(); col++) {
                Character o = field.getCharacter(row, col);
                if(o != null && o.thisIsHero() && rand.nextDouble() <= HUMAN_RECEIVING_ITEM_PROBABILITY) {
                    Human h = (Human)o;
                    Location location = h.getField().freeAdjacentLocation(h.getLocation());
                    if(location != null) {
                        //Faire un random pour savoir quel item larguer
                        Item item = null;
                        int randomItem = rand.nextInt(5);
                        switch(randomItem) { 
                        case 0 :
                            item = new HealthPack();
                            break;
                        case 1 :
                            item = new LiquidNitrogen();
                            break;
                        case 2 :
                            item = new Meal();
                            break;
                        case 3 :
                            item = new Shotgun();
                            break;
                        case 4 :
                            item = new WoodenStake();
                            break;    
                        }

                        field.placeItem(item, location);
                    }


                }

            }
        }

    }
}
