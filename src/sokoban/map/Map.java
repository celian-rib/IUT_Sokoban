package sokoban.map;

import java.util.HashSet;
import java.util.Optional;
import java.util.Vector;

import sokoban.Vector2;
import sokoban.exceptions.BuilderException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.Player;

public class Map {

    private MapObject[][] map;
    private Player player;

    public Map(MapBuilder builder) throws BuilderException {
        this.map = builder.build();
    }

    public void draw() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                map[i][j].draw();
            System.out.println();
        }
    }

    public MapObject addWall(Vector2 position) {
        return null;
    }

    public MapObject addBox(Vector2 position) {
        return null;
    }

    public MapObject addEmpty(Vector2 position) {
        return null;
    }

    public MapObject addDestination(Vector2 position) {
        return null;
    }

    public MapObject getObjectAtPosition(Vector2 position) {
        if(!positionIsInBoard(position))
            return null;
        return map[position.y][position.y];
    }

    public void setObjectAtPosition(Vector2 position, MapObject object) throws InvalidPositionException {
        if(!positionIsInBoard(position))
            throw new InvalidPositionException("Cannot set object at a position that is not contained in the board");
        map[position.y][position.y] = object;
    }

    public boolean moovAllowed(MoovableObject object, Vector2 direction) {
        MapObject origin = object.getMapObject();
        Vector2 predictedPosition = origin.getPosition().add(direction);
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);
        if(objectAtPredictedPosition == null)
            return false;
        return (objectAtPredictedPosition.TYPE == MapObject.ObjectType.EMPTY);
    }

    public void hardSwapObjects(Vector2 origin, Vector2 direction) throws InvalidPositionException {
        Vector2 predictedPosition = origin.add(direction);
        System.out.println(predictedPosition);
        if(!positionIsInBoard(origin) || !positionIsInBoard(predictedPosition))
            throw new InvalidPositionException("Cannot swap positions that are not both contained in the map");
        MapObject temp = getObjectAtPosition(predictedPosition);
        setObjectAtPosition(predictedPosition, getObjectAtPosition(origin));
        setObjectAtPosition(origin, temp);
    }

    public boolean positionIsInBoard(Vector2 position) {
        if(position.x < 0 || position.y < 0)
            return false;
        if(position.x >= map[0].length || position.y >= map.length)
            return false;
        return true;
    }

    public Player getMapPlayer() {
        if (player != null) // Memoïze
            return player;
        return getMapSet().stream().filter(o -> o != null).filter(o -> o.getClass() == Player.class).map(Player.class::cast).findFirst()
                .orElse(null);
    }

    public HashSet<MapObject> getMapSet() {
        HashSet<MapObject> set = new HashSet<>();
        for (MapObject[] objs : map)
            for (MapObject obj : objs)
                set.add(obj);
        return set;
    }
}
