//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban;

import java.util.concurrent.*;

import sokoban.exceptions.*;
import sokoban.map.*;
import sokoban.map.mapObject.*;
import sokoban.utils.*;

/**
 * Class that handle the program entry point and the game loop
 */
public class Sokoban {

    /**
     * Sokoban entry point
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // ======== LOAD ONE MAP DIRECTLY ============
        // try {
        //     var map = new Map(new sokoban.map.drawer.HighDetailsMapDrawer(),
        //     new sokoban.map.builder.MapFromFileBuilder("SokobanMaps/stucked__hard.txt"));
        //     gameLoop(map);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // ======== LOAD ONE MAP DIRECTLY ============

        try {
            //Retrieve map from the menu
            Map map = SokobanMenu.startSokobanMenu();
            //Start the game with the selected map
            gameLoop(map);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            for (StackTraceElement s : e.getStackTrace())
                System.err.println(s.toString());
        }
    }



    /**
     * Run a sokoban game
     * 
     * @param map map to play on
     * @throws NoPlayerException The loaded map does not contains any player
     * @throws InvalidPositionException An internal game method is miscalculating position ranges
     * @throws InterruptedException Timer error
     * @throws BuilderException
     */
    public static void gameLoop(Map map) throws NoPlayerException, InvalidPositionException,
            InterruptedException, BuilderException {

        boolean showTutorial = true;
        String errorDialog = null;

        map.draw();

        while (map.hasEmptyDestination()) {

            if (showTutorial) {
                SokobanMenu.drawLine(60);
                System.out.println("Please enter a direcion : U D R L");
                SokobanMenu.drawLine(60);
                System.out.println("- Directions can be chained (Eg : 'uuurdl')");
                System.out.println("- Enter 'reload' to reload the map");
                System.out.println("- Enter 'quit' to go back to the menu");
                SokobanMenu.drawLine(60);
            }
            if (errorDialog != null) {
                System.out.println("[ Hey Watchout ! ] " + errorDialog);
            }

            String inputString = ScannerUtils.awaitString("Dir > ").toUpperCase();

            if (inputString.equals("QUIT"))
                main(null);

            if (inputString.equals("RELOAD"))
                gameLoop(map.createCopyWithBaseComponent());


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
                    player.move(direction, map);
                    showTutorial = false;
                    errorDialog = null;
                } catch (InvalidMoveException e) {
                    errorDialog = e.getMessage();
                    break;
                }

                if (inputString.length() > 1)
                    TimeUnit.MILLISECONDS.sleep(300);

                map.draw();
            }
        }
        SokobanMenu.drawLine(60);
        System.out.println("You did it ! Well done.");
        main(null);
    }
}
