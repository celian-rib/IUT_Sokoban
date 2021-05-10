package sokoban.map.mapDatabase;

import java.util.Scanner;

public class MapDatabaseAdministration {
    
    private MapDatabase db;

    public MapDatabaseAdministration(MapDatabase db) {
        db = this.db;
    }

    public void drawAdministrationMenu() {
        System.out.println("DB Administration : ");
        System.out.println("O : Quit");
        System.out.println("1 : Initialize DB");
        
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine().trim()) {
            case "1":
                db.initDb();
            break;
        }
    }
}
