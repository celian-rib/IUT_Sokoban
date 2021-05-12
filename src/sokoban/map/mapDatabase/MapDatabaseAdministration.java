package sokoban.map.mapDatabase;

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
                    var rows = MapDatabase.getRowsFromFile(map.id ,"MapFile.txt");
                    db.addMap(map, rows);
                } catch (Exception e) {
                    System.err.println(e);
                }
                break;
        }
        drawAdministrationMenu(db);
    }

    private static void drawDb(MapDatabase db) {
        try {
            System.out.println();
            System.out.println("Maps : ");
            System.out.println(db.mapTableString());
            System.out.println();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
