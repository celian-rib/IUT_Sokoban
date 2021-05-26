//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.map.mapDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import sokoban.utils.ScannerUtils;

/**
 * Class that contains the database administration specific entry point
 * And all methods that let an admin user manage the database
 */
public class MapDatabaseAdministration {

    public static void main(String[] args) {
        try (MapDatabase database = new MapDatabase()) {
            drawAdministrationMenu(database);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    /**
     * Database administration menu
     * @param db database to work with
     */
    private static void drawAdministrationMenu(MapDatabase db) {
        System.out.println("DB Administration : ");
        System.out.println("O : Quit");
        System.out.println("1 : Initialize DB");
        System.out.println("2 : See DB");
        System.out.println("3 : Add map from file");
        System.out.println("4 : Delete map");
        System.out.println("5 : drop tables [DANGEROUS]");

        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine().trim()) {
            case "0":
                System.exit(0);
                break;
            case "1":
                db.initDb();
                break;
            case "2":
                drawDb(db);
                break;
            case "3":
                askMapFromFile(db);
                break;
            case "4":
                deleteMap(db);
                break;
            case "5":
                if(ScannerUtils.awaitString("type 'yes/delete'").equals("yes/delete"))
                    db.dropTables();
                break;
        }
        drawAdministrationMenu(db);
    }

    /**
     * Let you delete a map in the database from a user input in the terminal
     * @param db database
     */
    private static void deleteMap(MapDatabase db) {
        try {
            int id = ScannerUtils.awaitIntInRange("Id map : ", 0, db.getAvailableMapId() -1);
            db.removeMap(id);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Draw the database tables
     * @param db the actual database
     */
    private static void drawDb(MapDatabase db) {
        try {
            System.out.println();
            System.out.println("Maps : ");
            System.out.println(mapTableString(db));
            System.out.println();
            System.out.println();
            System.out.println("Rows : ");
            System.out.println(rowsTableString(db));
            System.out.println();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Add a map from a file path asked to the user
     * @param db the actual database
     */
    private static void askMapFromFile(MapDatabase db) {
        try {
            String name = ScannerUtils.awaitString("Map name :");
            String difficulty = ScannerUtils.awaitString("Map difficulty (easy, medium, hard) :");
            String path = ScannerUtils.awaitString("File name : ./SokobanMaps/");
            var map = db.new Map(name, difficulty);
            var rows = getRowsFromFile(map.id, "SokobanMaps/" + path);
            db.addMap(map, rows);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Create a readable string representing one map in the database
     * @param db database to work with
     * @return formated string
     */
    public static String mapTableString(MapDatabase db) {
        String result =
                String.format("| %3s | %20s | %15s |", "id", "name", "difficulty").replace(' ', '-')
                        + "\n";
        try {
            for (MapDatabase.Map m : db.getMaps())
                result += m.toString() + "\n";
            result += String.format("%48s", "").replace(' ', '-');
            return result;
        } catch (Exception e) {
            return "Table error";
        }
    }

    /**
     * Create a readable string representing one row in the database
     * @param db database to work with
     * @return formated string
     */
    public static String rowsTableString(MapDatabase db) {
        String result =
                String.format("| %6s | %6s | %30s |", "mapId", "rowId", "content").replace(' ', '-')
                        + "\n";
        try {
            for (MapDatabase.Row r : db.getRows())
                result += r.toString() + "\n";
            result += String.format("%52s", "").replace(' ', '-');
            return result;
        } catch (Exception e) {
            System.err.println(e);
            return "Table error";
        }
    }

    /**
     * Convert a file in an array of database row
     * @param mapId id of the map the rows will belong to
     * @param filePath path of the file to read
     * @return array list of database rows
     * @throws FileNotFoundException
     */
    public static ArrayList<MapDatabase.Row> getRowsFromFile(int mapId, String filePath)
            throws FileNotFoundException {
        ArrayList<MapDatabase.Row> rows = new ArrayList<MapDatabase.Row>();
        Scanner scanner = new Scanner(new File(filePath));
        int i = 0;
        while (scanner.hasNextLine()) {
            rows.add(new MapDatabase.Row(mapId, i, scanner.nextLine()));
            i++;
        }
        return rows;
    }
}
