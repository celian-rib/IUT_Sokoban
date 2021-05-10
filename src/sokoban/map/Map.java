package sokoban.map;

import java.lang.ProcessBuilder.Redirect.Type;
import java.util.Arrays;
import java.util.HashSet;

import sokoban.Vector2;
import sokoban.exceptions.BuilderException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.Player;

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
            throw new InvalidPositionException("Cannot set object at a position that is not contained in the board");
        map[object.getPosition().y][object.getPosition().x] = object;
    }

    public boolean handleMoovPosibility(MoovableObject object, Vector2 direction) throws Exception {
        Vector2 predictedPosition = object.getPosition().add(direction); // Position where we want to moov
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);

        if (objectAtPredictedPosition == null) // Not on map
            return false;

        // Moov is going on an empty space (This is correct)
        if (objectAtPredictedPosition.TYPE == MapObject.ObjectType.EMPTY || objectAtPredictedPosition.TYPE == MapObject.ObjectType.DESTINATION)
            return true;

        // If the wanted position is moovable -> the moov if still posssible as long as
        // we can push this moovable object
        if (objectAtPredictedPosition instanceof MoovableObject) {
            MoovableObject pushable = (MoovableObject) objectAtPredictedPosition;
            // We check if the moov is allowed for the box, the recursion will check if a
            // box can push a box and more ...
            boolean boxMoovAllowed = handleMoovPosibility(pushable, direction);
            if (boxMoovAllowed)
                pushable.moov(direction, this); // We push this pushable
            return boxMoovAllowed;
        }

        return false;
    }

    public void hardSwapObjects(Vector2 origin, Vector2 direction) throws Exception {
        Vector2 predictedPosition = origin.add(direction);

        if (!positionIsInBoard(origin) || !positionIsInBoard(predictedPosition))
            throw new InvalidPositionException("Cannot swap positions that are not both contained in the map");

        MapObject newNext = getObjectAtPosition(origin).createPositionedCopy(predictedPosition);
        MapObject newOrigin = getObjectAtPosition(predictedPosition).createPositionedCopy(origin);

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
        return getMapSet().stream().filter(o -> o.TYPE == MapObject.ObjectType.BOX).count() > 0;
    }
    
    public boolean hasEmptyDestination() {
        if(getMapPlayer().isOnDestination())
            return true;
        return getMapSet().stream().filter(o -> o.TYPE == MapObject.ObjectType.DESTINATION).count() > 0;
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
