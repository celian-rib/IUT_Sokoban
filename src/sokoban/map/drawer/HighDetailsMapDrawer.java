/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.map.drawer;

import java.util.HashMap;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.MapObject;

/**
 *
 * @author criboulet
 */
public class HighDetailsMapDrawer implements MapDrawer {

    private static String mapString;

    private HashMap<MapObject.ObjectType, String> objectsStrings = new HashMap<MapObject.ObjectType,String>() {{
        put(MapObject.ObjectType.BOX, "⌜---⌝\n|OOO|\n⌞---⌟");
        put(MapObject.ObjectType.PLAYER, "  O  \n /|\\ \n / \\ ");
        put(MapObject.ObjectType.EMPTY, "     \n  .  \n     ");
        put(MapObject.ObjectType.WALL, "#####\n#####\n#####");
        put(MapObject.ObjectType.DESTINATION, "\\   /\n  X  \n/   \\");
    }};
   
    private HashMap<MapObject.ObjectType, String> hasDestinationMoovablesStrings = new HashMap<MapObject.ObjectType,String>() {{
        put(MapObject.ObjectType.BOX, "⌜---⌝\n|OXO|\n⌞---⌟");
        put(MapObject.ObjectType.PLAYER, "\\ O /\n /|\\ \n// \\\\");
    }};

    @Override
    public void draw(MapObject[][] map) {
        mapString = "";
        for (int i = 0; i < map.length; i++)
            drawLine(map[i]);
        System.out.println(mapString);
    }

    private void drawLine(MapObject[] line) {
        for (int i = 0; i < 3; i++) {
            for (MapObject obj : line) {
                if(obj instanceof MoovableObject) {
                    MoovableObject moovable = (MoovableObject) obj;
                    if(moovable.isOnDestination()) {
                        mapString += hasDestinationMoovablesStrings.get(obj.TYPE).split("\n")[i];
                        continue;
                    }
                }
                mapString += objectsStrings.get(obj.TYPE).split("\n")[i];
            }
            mapString += "\n";
        }
    }
}
