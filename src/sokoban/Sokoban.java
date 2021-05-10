package sokoban;

import java.util.Scanner;

import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.NoPlayerException;
import sokoban.map.Map;
import sokoban.map.MapFromFileBuilder;
import sokoban.map.mapObject.Player;

/**
 *
 * @author celia
 */
public class Sokoban {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //MapFromFileBuilder builder = new MapFromFileBuilder("C:\\Users\\celia\\Desktop\\sokoban\\MapFile3.txt");
        MapFromFileBuilder builder = new MapFromFileBuilder("\\\\iut.bx1\\Etudiants\\Home\\criboulet\\Desktop\\sokoban\\MapFile3.txt");

        try {
            Map map = new Map(builder);
            gameLoop(map);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            for (StackTraceElement s : e.getStackTrace())
                System.out.println(s.toString());
        }
    }

    public static void gameLoop(Map map) throws Exception {

        while (map.hasEmptyDestination()) {

            Player player = map.getMapPlayer();

            if (player == null)
                throw new NoPlayerException("No player found on map");

            map.draw();
            System.out.println("Give a direction : U D R L");

            Scanner scanner = new Scanner(System.in);
            char input = scanner.nextLine().trim().toUpperCase().charAt(0);
            
            if (!Vector2.CHAR_DIRECTION.containsKey(input)) {
                System.out.println("Invalid input");
                continue;
            }

            Vector2 direction = Vector2.CHAR_DIRECTION.get(input);
            try {
                player.moov(direction, map);
            } catch (InvalidMoovException e) {
                System.out.println(e.getMessage());
                continue;
            }
        }
    }
}
