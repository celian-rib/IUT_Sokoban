/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.map;

import java.util.HashMap;

import sokoban.map.mapObject.MapObject;

/**
 *
 * @author criboulet
 */
public class HighDetailsMapDrawer implements MapDrawer {

    private static String mapString;

    // private static enum WALL_TYPE {
    // TOP, BOTTOM, RIGHT, LEFT, TOP_RIGHT
    // }

    // § are the white spaces
    // private static String bottomWall = "§§§§§\n§§§§§\n-----";
    // private static String topWall = "-----\n§§§§§\n§§§§§";
    // private static String leftWall = "|§§§§\n|§§§§\n|§§§§";
    // private static String rightWall = "§§§§|\n§§§§|\n§§§§|";
    private static String wallString = "#####";
    private static String playerString = "  O  \n /|\\ \n / \\ ";
    // private static String boxString = ;

    private HashMap<MapObject.ObjectType, String> objectsStrings = new HashMap<MapObject.ObjectType,String>() {{
        put(MapObject.ObjectType.BOX, "⌜---⌝\n|OOO|\n⌞---⌟");
        put(MapObject.ObjectType.PLAYER, "  O  \n /|\\ \n / \\ ");
        put(MapObject.ObjectType.EMPTY, "     \n     \n     ");
        put(MapObject.ObjectType.WALL, "#####\n#####\n#####");
        put(MapObject.ObjectType.DESTINATION, "\\   /\n  X  \n/   \\");
    }};

    /**
 * 3x5
-------------------
|  O   ####
| /|\  #--#
| / \  #### |____|
-------------------
 */

    MapObject[][] map;

    @Override
    public void draw(MapObject[][] map) {
        this.map = map;
        mapString = "";
        for (int i = 0; i < map.length; i++) {
            drawLine(map[i]);
        }
        System.out.println(mapString);
    }

    private void drawLine(MapObject[] line) {
        for (int i = 0; i < 3; i++) {
            for (MapObject obj : line) {
                mapString += objectsStrings.get(obj.TYPE).split("\n")[i];
            }
            mapString += "\n";
        }
    }

    // private static void getPlayerCell(int ligne) {

    // }
}
