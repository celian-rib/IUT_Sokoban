package sokoban.map;

import java.util.Arrays;
import java.util.HashSet;

import sokoban.Vector2;
import sokoban.exceptions.BuilderException;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.builder.MapBuilder;
import sokoban.map.drawer.MapDrawer;
import sokoban.map.mapObject.Destination;
import sokoban.map.mapObject.Empty;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.Player;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Map {

    private final MapDrawer drawer;
    private MapObject[][] map;

    public Map(MapDrawer drawer, MapBuilder builder) throws BuilderException {
        this.map = builder.build();
        this.drawer = drawer;
    }

    public void draw() {
        drawer.draw(map);
    }

    public MapObject getObjectAtPosition(Vector2 position) {
        if (!positionIsInBoard(position))
            return null;
        return map[position.y][position.x];
    }

    public void setObjectOnMap(MapObject object) throws InvalidPositionException {
        if (!positionIsInBoard(object.getPosition()))
            throw new InvalidPositionException(
                    "Cannot set object at a position that is not contained in the board");
        map[object.getPosition().y][object.getPosition().x] = object;
    }

    public boolean handleMoovPosibility(MoovableObject object, Vector2 direction)
            throws InvalidMoovException, InvalidPositionException, UnsupportedOperationException {
        Vector2 predictedPosition = object.getPosition().add(direction); // Position where we want
                                                                         // to moov
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);

        if (objectAtPredictedPosition == null) // Not on map
            return false;

        // Moov is going on an empty space (This is correct)
        if (objectAtPredictedPosition.TYPE == MapObject.ObjectType.EMPTY
                || objectAtPredictedPosition.TYPE == MapObject.ObjectType.DESTINATION)
            return true;

        // If the wanted position is moovable -> the moov if still posssible as long as
        // we can push this moovable object
        if (objectAtPredictedPosition instanceof MoovableObject)
            return handleMoovPosibility((MoovableObject) objectAtPredictedPosition, direction);

        return false;
    }

    public void moovObject(MoovableObject object, Vector2 direction) throws UnsupportedOperationException, InvalidMoovException, InvalidPositionException {
        if (!handleMoovPosibility(object, direction))
            throw new InvalidMoovException("You can't go in this direction...");
        
        Vector2 currentPosition = object.getPosition();
        Vector2 nextPosition = currentPosition.add(direction);
        MapObject nextPositionObject = getObjectAtPosition(nextPosition);
    
        if(nextPositionObject instanceof MoovableObject){
            moovObject((MoovableObject) nextPositionObject, direction);
        }

        nextPositionObject = getObjectAtPosition(nextPosition);


        if(object.isOnDestination()){
            System.out.println("revert");
            setObjectOnMap(new Destination(nextPosition));
            object.setIsOnDestination(nextPositionObject.TYPE == ObjectType.DESTINATION);
        }
        
        if(nextPositionObject.TYPE == ObjectType.DESTINATION){
            System.out.println("on dest");
            if(!object.isOnDestination())
                setObjectOnMap(new Empty(nextPosition));
            object.setIsOnDestination(true);
        }

        hardSwapPositions(currentPosition, nextPosition);
    }

    private void hardSwapPositions(Vector2 first, Vector2 second) throws InvalidPositionException {
        MapObject newSecond = getObjectAtPosition(first).createPositionedCopy(second);
        MapObject newFirst = getObjectAtPosition(second).createPositionedCopy(first);

        setObjectOnMap(newSecond);
        setObjectOnMap(newFirst);
    }

    public boolean positionIsInBoard(Vector2 position) {
        if (position.x < 0 || position.y < 0)
            return false;
        if (position.x >= map[0].length || position.y >= map.length)
            return false;
        return true;
    }

    public boolean hasBox() {
        return getMapSet().stream().filter(o -> o.TYPE == MapObject.ObjectType.BOX).count() > 0;
    }

    public boolean hasEmptyDestination() {
        if (getMapPlayer().isOnDestination())
            return true;
        return getMapSet().stream().filter(o -> o.TYPE == MapObject.ObjectType.DESTINATION)
                .count() > 0;
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
