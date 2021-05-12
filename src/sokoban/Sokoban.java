package sokoban;

import java.util.Scanner;
import java.util.concurrent.*;

import sokoban.exceptions.*;
import sokoban.map.*;
import sokoban.map.mapObject.*;

/**
 *
 * @author celia
 */
public class Sokoban {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapFromFileBuilder builder = new MapFromFileBuilder("MapFile4.txt");

        // MapDrawer drawer = new LowDetailsMapDrawer();
        MapDrawer drawer = new HighDetailsMapDrawer();

        try {
            Map map = new Map(drawer, builder);
            gameLoop(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            for (StackTraceElement s : e.getStackTrace())
                System.err.println(s.toString());
        }
    }

    public static void gameLoop(Map map) throws NoPlayerException, InvalidPositionException, InterruptedException {

        boolean showTutorial = true;
        String errorDialog = null;

        map.draw();
    
        while (map.hasEmptyDestination()) {
           
            if (showTutorial) {
                System.out.println("Please enter a direcion : U D R L");
                System.out.println("Directions can be chained (Eg : 'uuurdl')");
            }
            if (errorDialog != null) {
                System.out.println("[ Hey Watchout ! ] " + errorDialog);
            }
            System.out.print("Dir > ");

            Scanner scanner = new Scanner(System.in);
            String inputString = scanner.nextLine().trim().toUpperCase();
            if (inputString.length() == 0)
                continue;

            for (char input : inputString.toCharArray()) {
                Player player = map.getMapPlayer();

                if (player == null)
                    throw new NoPlayerException("Error : no player found on map");

                if (!Vector2.CHAR_DIRECTION.containsKey(input)) {
                    errorDialog = input + " is not a valid direction...";
                    break;
                }

                Vector2 direction = Vector2.CHAR_DIRECTION.get(input);
                try {
                    player.moov(direction, map);
                    showTutorial = false;
                    errorDialog = null;
                } catch (InvalidMoovException e) {
                    errorDialog = e.getMessage();
                    break;
                }

                if(inputString.length() > 1)
                    TimeUnit.MILLISECONDS.sleep(300);

                map.draw();
            }
        }
    }
}
