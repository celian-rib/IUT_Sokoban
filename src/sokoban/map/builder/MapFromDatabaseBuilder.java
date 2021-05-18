package sokoban.map.builder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapDatabase.MapDatabase;
import sokoban.map.mapObject.*;
import sokoban.map.mapObject.MapObject;

public class MapFromDatabaseBuilder implements MapBuilder {

    private int mapId;
    private MapDatabase db;

    public MapFromDatabaseBuilder(int mapId, MapDatabase db) {
        this.mapId = mapId;
        this.db = db;
    }

    public MapObject[][] build() throws BuilderException {
        ArrayList<MapObject[]> rawMap = new ArrayList<>();
        try {
            int rowCount = -1;
            boolean mapHasPlayer = false;
            for (MapDatabase.Row row : db.getRows()) {
                if(row.mapId != mapId)
                    continue;

                char[] ligne = row.content.toCharArray();

                //Check if all rowCount (= ligne length) are the same in the file
                if (rowCount == -1)
                    rowCount = ligne.length;
                else if (rowCount != ligne.length)
                    throw new BuilderException("Unexpected ligne length in the map source");

                rawMap.add(new MapObject[rowCount]);

                for (int i = 0; i < ligne.length; i++) {
                    int currentLigneIndex = rawMap.size() - 1;
                    switch (Character.toLowerCase(ligne[i])) {
                        default:
                            throw new BuilderException("Unexpected char in the map source");
                        case '.':
                            rawMap.get(currentLigneIndex)[i] = new Empty(i, currentLigneIndex);
                            break;
                        case 'x':
                            rawMap.get(currentLigneIndex)[i] = new Destination(i, currentLigneIndex);
                            break;
                        case 'p':
                            if(mapHasPlayer)
                                throw new BuilderException("A map cannot have more than one player in the map source");
                            mapHasPlayer = true;
                            rawMap.get(currentLigneIndex)[i] = new Player(i, currentLigneIndex);
                            break;
                        case 'c':
                            rawMap.get(currentLigneIndex)[i] = new Box(i, currentLigneIndex);
                            break;
                        case '#':
                            rawMap.get(currentLigneIndex)[i] = new Wall(i, currentLigneIndex);
                            break;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return (MapObject[][]) rawMap.toArray(new MapObject[rawMap.size()][]);
    };
}
