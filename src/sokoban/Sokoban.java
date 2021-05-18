package sokoban;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.*;

import sokoban.exceptions.*;
import sokoban.map.*;
import sokoban.map.builder.*;
import sokoban.map.drawer.*;
import sokoban.map.mapObject.*;
import sokoban.map.mapDatabase.*;

/**
 *
 * @author celia
 */
public class Sokoban {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        gameMenu();

        // try {
        // // MapFromDatabaseBuilder builder = new MapFromDatabaseBuilder(0, new MapDatabase());
        // MapFromFileBuilder builder = new MapFromFileBuilder("MapFile4.txt");

        // // MapDrawer drawer = new LowDetailsMapDrawer();
        // MapDrawer drawer = new HighDetailsMapDrawer();

        // Map map = new Map(drawer, builder);
        // gameLoop(map);
        // } catch (Exception e) {
        // System.err.println(e.getMessage());
        // for (StackTraceElement s : e.getStackTrace())
        // System.err.println(s.toString());
        // }
    }

    private static void drawLine(int length) {
        for (int i = 0; i < length; i++)
            System.out.print("=");
        System.out.println();
    }

    public static void gameMenu() {
        try {

            MapDatabase db = new MapDatabase();
            
            drawLine(60);
            MapDatabase.Map map = mapSelection(db);

            drawLine(60);
            MapDrawer drawer = drawerSelection();
            
            MapFromDatabaseBuilder builder = new MapFromDatabaseBuilder(map.id, db);

            gameLoop(new Map(drawer, builder));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            for (StackTraceElement s : e.getStackTrace())
                System.err.println(s.toString());
        }
    }

    private static MapDatabase.Map mapSelection(MapDatabase db) throws SQLException {
        ArrayList<MapDatabase.Map> maps = db.getMaps();
        System.out.println("Choose a map : ");
        int i = 0;
        for (MapDatabase.Map map : maps) {
            i++;
            System.out.println(i + " -  " + map.name + " [" + map.difficulty + "]");
        }
        return maps.get(ScannerUtils.awaitIntInRange("Num > ", 1, maps.size()) - 1);
    }

    private static MapDrawer drawerSelection() {
        System.out.println("Choose your graphics quality : ");
        System.out.println("1 - High details and big drawings (Recommended)");
        System.out.println("2 - Compact");
        MapDrawer drawer = null;
        switch (ScannerUtils.awaitIntInRange("Num > ", 1, 2)) {
            case 1:
                drawer = new HighDetailsMapDrawer();
                break;
            case 2:
                drawer = new LowDetailsMapDrawer();
                break;
        }
        if(drawer == null)
            return drawerSelection();
        return drawer;
    }

    /**
     * Run a sokoban game
     * 
     * @param map map to play on
     * @throws NoPlayerException The loaded map does not contains any player
     * @throws InvalidPositionException An internal game method is miscalculating position ranges
     * @throws InterruptedException Timer error
     */
    public static void gameLoop(Map map)
            throws NoPlayerException, InvalidPositionException, InterruptedException {

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
            
            String inputString = ScannerUtils.awaitString("Dir > ").toUpperCase();

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
    }

}
