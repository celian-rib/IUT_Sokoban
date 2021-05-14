package sokoban.map;

import java.util.Arrays;
import java.util.HashSet;

import sokoban.Vector2;
import sokoban.exceptions.BuilderException;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.mapObject.Empty;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.Player;

public class Map {

    /**
     * Drawer used to display this map
     * 
     * @see MapDrawer
     */
    private final MapDrawer drawer;

    /**
     * Actual map represented as a 2D array of map object This let join actual array
     * positions with in game position for every objects
     */
    private MapObject[][] map;

    /**
     * Create a new map with a drawer from a builder
     * 
     * @param drawer  drawer that will be used to display the map
     * @param builder builder use to create the map
     * @throws BuilderException
     */
    public Map(MapDrawer drawer, MapBuilder builder) throws BuilderException {
        this.map = builder.build();
        this.drawer = drawer;
    }

    /**
     * Draw this map with the display result depending on which drawer this map is
     * using
     */
    public void draw() {
        drawer.draw(map);
    }

    /**
     * Gives the object at a certain position in the map
     * 
     * @param position position to look for
     * @return object at the position
     */
    public MapObject getObjectAtPosition(Vector2 position) {
        if (!positionIsInBoard(position))
            return null;
        return map[position.y][position.x];
    }

    /**
     * Set on the map an object The position stored in this object will be used
     * 
     * @param object object to set
     * @throws InvalidPositionException
     */
    public void setObjectOnMap(MapObject object) throws InvalidPositionException {
        if (!positionIsInBoard(object.getPosition()))
            throw new InvalidPositionException("Cannot set object at a position that is not contained in the board");
        map[object.getPosition().y][object.getPosition().x] = object;
    }

    public boolean handleMoovPosibility(MoovableObject object, Vector2 direction)
            throws InvalidMoovException, InvalidPositionException, UnsupportedOperationException {
        Vector2 predictedPosition = object.getPosition().add(direction); // Position where we want to moov
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);

        if (objectAtPredictedPosition == null) // Not on map
            return false;

        // Moov is going on an empty space (This is correct)
        if (objectAtPredictedPosition.isEmpty() || objectAtPredictedPosition.isDestination())
            return true;

        // If the wanted position is moovable -> the moov if still posssible as long as
        // we can push this moovable object
        if (objectAtPredictedPosition instanceof MoovableObject)
            return handleMoovPosibility((MoovableObject) objectAtPredictedPosition, direction);

        return false;
    }

    public void moveObject(MapObject object, Vector2 direction) throws InvalidPositionException {
        Vector2 predictedPosition = object.getPosition().add(direction);

        if (!positionIsInBoard(object.getPosition()) || !positionIsInBoard(predictedPosition))
            throw new InvalidPositionException("Cannot swap positions that are not both contained in the map");

        if (getObjectAtPosition(predictedPosition).isBox())
            moveObject(getObjectAtPosition(predictedPosition), direction);

        if (getObjectAtPosition(predictedPosition).isDestination() && object instanceof MoovableObject) {
            MoovableObject obj = ((MoovableObject) object);
            obj.setIsOnDestination(true);
            setObjectOnMap(new Empty(predictedPosition));
        }

        if (getObjectAtPosition(predictedPosition) instanceof MoovableObject) {
            MoovableObject predictedObj = ((MoovableObject) getObjectAtPosition(predictedPosition));
            MoovableObject obj = ((MoovableObject) object);
            obj.setIsOnDestination(predictedObj.isOnDestination());
        }

        MapObject newNext = object.createPositionedCopy(predictedPosition);
        MapObject newOrigin = getObjectAtPosition(predictedPosition).createPositionedCopy(object.getPosition());

        setObjectOnMap(newNext);
        setObjectOnMap(newOrigin);

    }

    public boolean positionIsInBoard(Vector2 position) {
        if (position.x < 0 || position.y < 0)
            return false;
        if (position.x >= map[0].length || position.y >= map.length)
            return false;
        return true;
    }

    public boolean hasBox() {
        return getMapSet().stream().filter(o -> o.isBox()).count() > 0;
    }

    public boolean hasEmptyDestination() {
        if (getMapPlayer().isOnDestination())
            return true;
        return getMapSet().stream().filter(o -> o.isDestination()).count() > 0;
    }

    public Player getMapPlayer() {
        return getMapSet().stream().filter(o -> o != null).filter(o -> o.getClass() == Player.class)
                .map(Player.class::cast).findFirst().orElse(null);
    }

    public HashSet<MapObject> getMapSet() {
        HashSet<MapObject> set = new HashSet<>();
        for (MapObject[] objs : map)
            set.addAll(Arrays.asList(objs));
        return set;
    }
}
