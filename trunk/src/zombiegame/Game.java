package zombiegame;

import graphics.Simulator;

public class Game {
    /**
     * @param args
     * @throws InterruptedException 
     */
    public static void main(String[] args) throws InterruptedException {

        Simulator s = new Simulator(50,50);
        s.simulate(1000);

        System.out.println("\n\n\n#########################################");
        if(s.nbHumansAlive() == 0)
        {
            System.out.println("All humans have been eaten!");
            System.out.println("Humans survived until wave " + s.getNumberOfTurns()); 
        }
        else
        {
            System.out.println("Humans won the game !");
        }

        System.out.println("END OF THE GAME");
        System.out.println("########################################");
    }

}
