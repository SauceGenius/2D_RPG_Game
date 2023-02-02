import game.Game;
import game.GameLoop;

public class Launcher {

    public static void main(String[] args)  {
        /** Launches GameLoop **/
        new Thread(new GameLoop(new Game())).start();

        /** RAM Heapsize **/
        long heapsize = Runtime.getRuntime().totalMemory();
        System.out.println("heapsize is :: " + heapsize/ 1000000 + " Mo");
    }
}
