package sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MapFromFileBuilder implements MapBuilder {

    String filePath;

    public MapFromFileBuilder(String filePath) {
        this.filePath = filePath;
    }

    public MapObject[][] build() throws BuilderException {
        ArrayList<MapObject[]> rawMap = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filePath))) {
            int rowCount = -1;
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
                    switch (ligne[i]) {
                        default:
                            throw new BuilderException("Unexpected char [" + ligne[i] + "] in the file at : " + filePath);
                        case '.':
                            rawMap.get(currentLigneIndex)[i] = null;
                            break;
                        case 'x':
                            rawMap.get(currentLigneIndex)[i] = new Destination(i, currentLigneIndex);
                            break;
                        case 'P':
                            rawMap.get(currentLigneIndex)[i] = null;
                            break;
                        case 'C':
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
