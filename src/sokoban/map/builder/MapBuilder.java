package sokoban.map.builder;

import java.util.ArrayList;
import java.util.Arrays;
import sokoban.exceptions.BuilderException;
import sokoban.map.mapObject.*;

public abstract class MapBuilder {

    /**
     * Used to prevent player duplication
     */
    private boolean mapHasPlayer = false;

    /**
     * Create map data content
     * 
     * @return map 2d array of map object
     * @throws BuilderException
     */
    public abstract MapObject[][] build() throws BuilderException;

    /**
     * Convert a char array into its conrresponding MapObject array
     * @param lineIndex position of this line in the map for the Y axis (Used to create positioning)
     * @param line char array to use
     * @return corresponding mapObject array
     * @throws BuilderException
     */
    protected MapObject[] convertCharLineToMapObjects(int lineIndex, char[] line) throws BuilderException {
        ArrayList<MapObject> rawLine = new ArrayList<>();
    
        for (int i = 0; i < line.length; i++) {
            switch (Character.toLowerCase(line[i])) {
                default:
                    throw new BuilderException("Unexpected char [" + line[i] + "] at line " + lineIndex);
                case '.':
                    rawLine.add(new Empty(i, lineIndex));
                    break;
                case 'x':
                    rawLine.add(new Destination(i, lineIndex));
                    break;
                case 'p':
                    if (mapHasPlayer)
                        throw new BuilderException("A map cannot have more than one player " + lineIndex);
                    mapHasPlayer = true;
                    rawLine.add(new Player(i, lineIndex));
                    break;
                case 'c':
                    rawLine.add(new Box(i, lineIndex));
                    break;
                case '#':
                    rawLine.add(new Wall(i, lineIndex));
                    break;
            }
        }

        return Arrays.copyOf(rawLine.toArray(), rawLine.size(), MapObject[].class);
    }
}
