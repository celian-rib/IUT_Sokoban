package sokoban.map.mapDatabase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapDatabaseAdministration {

    public static void main(String[] args) {
        try (MapDatabase database = new MapDatabase()) {
            drawAdministrationMenu(database);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
    }

    private static void drawAdministrationMenu(MapDatabase db) {
        System.out.println("DB Administration : ");
        System.out.println("O : Quit");
        System.out.println("1 : Initialize DB");
        System.out.println("2 : See DB");
        System.out.println("3 : Add map from file");
        System.out.println("4 : drop tables");

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
                try {
                    var map = db.new Map("Map de test", "facile");
                    var rows = getRowsFromFile(map.id, "MapFile2.txt");
                    db.addMap(map, rows);
                } catch (Exception e) {
                    System.err.println(e);
                }
                break;
            case "4":
                db.dropTables();
                break;
        }
        drawAdministrationMenu(db);
    }

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
