package sokoban.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapObject.Box;
import sokoban.map.mapObject.Destination;
import sokoban.map.mapObject.Empty;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.Player;
import sokoban.map.mapObject.Wall;

public class MapFromFileBuilder implements MapBuilder {
    String filePath;

    public MapFromFileBuilder(String filePath) {
        this.filePath = filePath;
    }

    public MapObject[][] build() throws BuilderException {
        ArrayList<MapObject[]> rawMap = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int rowCount = -1;
            boolean mapHasPlayer = false;
            while (scanner.hasNextLine()) {
                char[] ligne = scanner.nextLine().toCharArray();

                //Check if all rowCount (= ligne length) are the same in the file
                if (rowCount == -1)
                    rowCount = ligne.length;
                else if (rowCount != ligne.length)
                    throw new BuilderException("Unexpected ligne length in the file at " + filePath);

                rawMap.add(new MapObject[rowCount]);

                for (int i = 0; i < ligne.length; i++) {
                    int currentLigneIndex = rawMap.size() - 1;
                    switch (Character.toLowerCase(ligne[i])) {
                        default:
                            throw new BuilderException("Unexpected char [" + ligne[i] + "] in the file at : " + filePath);
                        case '.':
                            rawMap.get(currentLigneIndex)[i] = new Empty(i, currentLigneIndex);
                            break;
                        case 'x':
                            rawMap.get(currentLigneIndex)[i] = new Destination(i, currentLigneIndex);
                            break;
                        case 'p':
                            if(mapHasPlayer)
                                throw new BuilderException("A map cannot have more than one player in the file at : " + filePath);
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
        } catch (FileNotFoundException ex) {
            throw new BuilderException("Map file not found at path : " + filePath);
        }

        return (MapObject[][]) rawMap.toArray(new MapObject[rawMap.size()][]);
    }
}
