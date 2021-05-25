package sokoban.map;

import java.util.Arrays;
import java.util.HashSet;
import sokoban.map.builder.MapBuilder;
import sokoban.map.drawer.MapDrawer;
import sokoban.exceptions.*;
import sokoban.map.mapObject.*;
import sokoban.utils.Vector2;

public class Map {

    /**
     * Map drawer used to draw this map
     */
    private final MapDrawer drawer;
    /**
     * Map builder used to build this map
     */
    private final MapBuilder builder;

    /**
     * Actual map data
     */
    private MapObject[][] map;

    /**
     * Create a new map
     * 
     * @param drawer drawer to use for drawing the map
     * @param builder builder to use for the creation
     * @throws BuilderException
     */
    public Map(MapDrawer drawer, MapBuilder builder) throws BuilderException {
        this.map = builder.build();
        this.drawer = drawer;
        this.builder = builder;
    }

    /**
     * Draw this map using its drawer
     */
    public void draw() {
        drawer.draw(map);
    }

    /**
     * @param position position to look for
     * @return the object at the given position
     */
    private MapObject getObjectAtPosition(Vector2 position) {
        if (!containsPosition(position))
            return null;
        return map[position.y][position.x];
    }

    /**
     * Set on the map an object (The position used is the attribute position of the object)
     * 
     * @param object object to set on map
     * @throws InvalidPositionException
     */
    public void setObjectOnMap(MapObject object) throws InvalidPositionException {
        if (!containsPosition(object.getPosition()))
            throw new InvalidPositionException(
                    "Cannot set object at a position that is not contained in the board");
        map[object.getPosition().y][object.getPosition().x] = object;
    }

    /**
     * Check if a move is possible
     * 
     * @param object object that will move
     * @param direction directin in which the object will move (Normalized)
     * @return true if the object is able to move
     * @throws InvalidMoveException
     * @throws InvalidPositionException
     * @throws UnsupportedOperationException
     */
    private boolean handleMovePosibility(MovableObject object, Vector2 direction)
            throws InvalidMoveException, InvalidPositionException, UnsupportedOperationException {
        Vector2 predictedPosition = object.getPosition().add(direction);
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);

        if (objectAtPredictedPosition == null) // Not on map
            return false;

        // Move is going on an empty space (This is correct)
        if (objectAtPredictedPosition.TYPE == MapObject.ObjectType.EMPTY
                || objectAtPredictedPosition.TYPE == MapObject.ObjectType.DESTINATION)
            return true;

        // If the wanted position is movable -> the move if still posssible as long as
        // we can push this movable object
        if (objectAtPredictedPosition instanceof MovableObject)
            return handleMovePosibility((MovableObject) objectAtPredictedPosition, direction);

        return false;
    }

    /**
     * Moov an object on the map
     * 
     * @param object object to moov
     * @param direction direction in which the object is mooved (Normalized)
     * @throws UnsupportedOperationException
     * @throws InvalidMoveException
     * @throws InvalidPositionException
     */
    public void moveObject(MovableObject object, Vector2 direction)
            throws UnsupportedOperationException, InvalidMoveException, InvalidPositionException {
        if (!handleMovePosibility(object, direction))
            throw new InvalidMoveException("You can't go in this direction...");

        Vector2 currentPosition = object.getPosition();
        Vector2 nextPosition = currentPosition.add(direction);
        MapObject nextPositionObject = getObjectAtPosition(nextPosition);

        if (nextPositionObject instanceof MovableObject) {
            moveObject((MovableObject) nextPositionObject, direction);
        }

        nextPositionObject = getObjectAtPosition(nextPosition);

        if (object.isOnDestination()) {
            setObjectOnMap(new Destination(nextPosition));
            object.setIsOnDestination(nextPositionObject.TYPE == MapObject.ObjectType.DESTINATION);
        }

        if (nextPositionObject.TYPE == MapObject.ObjectType.DESTINATION) {
            if (!object.isOnDestination())
                setObjectOnMap(new Empty(nextPosition));
            object.setIsOnDestination(true);
        }

        hardSwapPositions(currentPosition, nextPosition);
    }

    /**
     * Swap two object on the map with two given positions
     * 
     * @param first first pos
     * @param second second pos
     * @throws InvalidPositionException
     */
    private void hardSwapPositions(Vector2 first, Vector2 second) throws InvalidPositionException {
        MapObject newSecond = getObjectAtPosition(first).createPositionedCopy(second);
        MapObject newFirst = getObjectAtPosition(second).createPositionedCopy(first);

        setObjectOnMap(newSecond);
        setObjectOnMap(newFirst);
    }

    /**
     * Check if a position is contained in the map
     * 
     * @param position position to check
     * @return
     */
    private boolean containsPosition(Vector2 position) {
        if (position.x < 0 || position.y < 0)
            return false;
        if (position.x >= map[0].length || position.y >= map.length)
            return false;
        return true;
    }

    /**
     * @return true if their is any destination on the map that is empty (No moovable object on it)
     */
    public boolean hasEmptyDestination() {
        if (getMapPlayer().isOnDestination())
            return true;
        return getMapSet().stream().filter(o -> o.TYPE == MapObject.ObjectType.DESTINATION)
                .count() > 0;
    }

    /**
     * @return the actual object that is the player on the map null if not found
     */
    public Player getMapPlayer() {
        return getMapSet()
                .stream()
                .filter(o -> o != null)
                .filter(o -> o.getClass() == Player.class)
                .map(Player.class::cast)
                .findFirst()
                .orElse(null);
    }

    /**
     * @return the map 2d array in a one dimmensional HashSet
     */
    private HashSet<MapObject> getMapSet() {
        HashSet<MapObject> set = new HashSet<>();
        for (MapObject[] objs : map)
            set.addAll(Arrays.asList(objs));
        return set;
    }

    /**
     * Create a new map with the same drawer and builder
     * 
     * This map state are not used to create the copy
     * 
     * @return clean copy of the map
     * @throws BuilderException
     */
    public Map createCopyWithBaseComponent() throws BuilderException {
        return new Map(drawer, builder);
    }
}
