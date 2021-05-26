//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.map.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapObject.MapObject;

/**
 * Class that let you create a map from a direct file path
 */
public class MapFromFileBuilder extends MapBuilder {
    String filePath;

    /**
     * Create a map builder
     * @param filePath path this builder will use to build the map
     */
    public MapFromFileBuilder(String filePath) {
        this.filePath = filePath;
    }

    public MapObject[][] build() throws BuilderException {
        ArrayList<MapObject[]> rawMap = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int expectedLineLength = -1;
            int lineIndex = 0;

            while (scanner.hasNextLine()) {
                char[] line = scanner.nextLine().toCharArray();

                //Check if all rowCount (= line length) are the same in the file
                if (expectedLineLength == -1)
                    expectedLineLength = line.length;
                else if (expectedLineLength != line.length)
                    throw new BuilderException("Unexpected ligne length in the file at " + filePath);

                rawMap.add(convertCharLineToMapObjects(lineIndex, line));
                lineIndex++;
            }
        } catch (FileNotFoundException ex) {
            throw new BuilderException("Map file not found at path : " + filePath);
        }

        return (MapObject[][]) rawMap.toArray(new MapObject[rawMap.size()][]);
    }
}
