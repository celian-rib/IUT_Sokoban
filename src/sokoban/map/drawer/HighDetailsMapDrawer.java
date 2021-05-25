//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package sokoban.map.drawer;

import java.util.HashMap;
import sokoban.map.mapObject.MovableObject;
import sokoban.map.mapObject.MapObject;

/**
 * Draw a map with better details, each objects is repesentd with 3x5 characters in terminal
 */
public class HighDetailsMapDrawer implements MapDrawer {

    /**
     * Final map string, use to accumulate the result, then this whole string is then displayed
     */
    private static String mapString;

    /**
     * Strings for each line and for each objects One object is draw on trhee line
     */
    private HashMap<MapObject.ObjectType, String> objectsStrings =
            new HashMap<MapObject.ObjectType, String>() {
                {
                    put(MapObject.ObjectType.BOX, "⌜---⌝\n|OOO|\n⌞---⌟");
                    put(MapObject.ObjectType.PLAYER, "  O  \n /|\\ \n / \\ ");
                    put(MapObject.ObjectType.EMPTY, "     \n  .  \n     ");
                    put(MapObject.ObjectType.WALL, "#####\n#####\n#####");
                    put(MapObject.ObjectType.DESTINATION, "\\   /\n  X  \n/   \\");
                }
            };

    /**
     * Alternative objects strings for object that goes onto destinations (Movables)
     */
    private HashMap<MapObject.ObjectType, String> hasDestinationMovablesStrings =
            new HashMap<MapObject.ObjectType, String>() {
                {
                    put(MapObject.ObjectType.BOX, "⌜---⌝\n|OXO|\n⌞---⌟");
                    put(MapObject.ObjectType.PLAYER, "\\ O /\n /|\\ \n// \\\\");
                }
            };

    @Override
    public void draw(MapObject[][] map) {
        mapString = "";
        for (int i = 0; i < map.length; i++)
            drawLine(map[i]);
        System.out.println(mapString);
    }

    /**
     * Draw a map's "Line", so it accumulate three characters line at once
     */
    private void drawLine(MapObject[] line) {
        for (int i = 0; i < 3; i++) {
            for (MapObject obj : line) {
                if (obj instanceof MovableObject) {
                    MovableObject movable = (MovableObject) obj;
                    if (movable.isOnDestination()) {
                        mapString += hasDestinationMovablesStrings.get(obj.TYPE).split("\n")[i];
                        continue;
                    }
                }
                mapString += objectsStrings.get(obj.TYPE).split("\n")[i];
            }
            mapString += "\n";
        }
    }
}
