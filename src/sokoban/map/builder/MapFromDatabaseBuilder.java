//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.map.builder;

import java.sql.SQLException;
import java.util.ArrayList;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapDatabase.MapDatabase;
import sokoban.map.mapObject.MapObject;

/**
 * Class that let you create a map from a map stored in a database
 */
public class MapFromDatabaseBuilder extends MapBuilder {

    private int mapId;
    private MapDatabase db;

    /**
     * Constructor of the builder
     * @param mapId id of the map the build
     * @param db database containing the map
     */
    public MapFromDatabaseBuilder(int mapId, MapDatabase db) {
        this.mapId = mapId;
        this.db = db;
    }

    public MapObject[][] build() throws BuilderException {
        ArrayList<MapObject[]> rawMap = new ArrayList<>();
        try {
            int rowCount = -1;
            int lineIndex = 0;
            for (MapDatabase.Row row : db.getRows()) {
                if(row.mapId != mapId)
                    continue;

                char[] line = row.content.toCharArray();

                //Check if all rowCount (= line length) are the same in the file
                if (rowCount == -1)
                    rowCount = line.length;
                else if (rowCount != line.length)
                    throw new BuilderException("Unexpected line length in the map source");

                rawMap.add(convertCharLineToMapObjects(lineIndex, line));
                lineIndex++;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return (MapObject[][]) rawMap.toArray(new MapObject[rawMap.size()][]);
    };
}
