//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban;

import java.sql.SQLException;
import java.util.ArrayList;

import sokoban.exceptions.*;
import sokoban.map.*;
import sokoban.map.builder.*;
import sokoban.map.drawer.*;
import sokoban.utils.*;
import sokoban.map.mapDatabase.*;

/**
 * All methods containing the game menu
 */
public class SokobanMenu {

    /**
     * Handle the game menu (Users inputs and game creation)
     * @throws SQLException
     * @throws BuilderException
     */
    public static Map startSokobanMenu() throws SQLException, BuilderException {

        MapDatabase db = new MapDatabase();

        drawLine(60);
        MapDatabase.Map map = mapSelection(db);

        drawLine(60);
        MapDrawer drawer = drawerSelection();

        MapFromDatabaseBuilder builder = new MapFromDatabaseBuilder(map.id, db);

        return new Map(drawer, builder);
    }

    /**
     * Draw a line of '-' in the terminal
     */
    static void drawLine(int length) {
        for (int i = 0; i < length; i++)
            System.out.print("=");
        System.out.println();
    }

    /**
     * Ask the user to select a map
     * 
     * @param db database that contains the maps
     * @return map of the database choosed by the player
     * @throws SQLException
     */
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

    /**
     * Ask the user to select a drawer
     * 
     * @return the selected drawer
     */
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
        if (drawer == null)
            return drawerSelection();
        return drawer;
    }
}
