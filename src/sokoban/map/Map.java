package sokoban.map;

import java.util.HashSet;

import sokoban.Vector2;
import sokoban.exceptions.BuilderException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.mapObject.MapObject;
import sokoban.map.mapObject.MoovableObject;
import sokoban.map.mapObject.Player;

public class Map {

    private MapObject[][] map;

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

    public boolean moovAllowed(MoovableObject object, Vector2 direction) {
        MapObject origin = object.getMapObject();
        Vector2 predictedPosition = origin.getPosition().add(direction);
        MapObject objectAtPredictedPosition = getObjectAtPosition(predictedPosition);
        if (objectAtPredictedPosition == null)
            return false;
        return (objectAtPredictedPosition.TYPE == MapObject.ObjectType.EMPTY);
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

    public Player getMapPlayer() {
        /** @TODO Memoïze */ 
        return getMapSet().stream().filter(o -> o != null).filter(o -> o.getClass() == Player.class)
                .map(Player.class::cast).findFirst().orElse(null);
    }

    public HashSet<MapObject> getMapSet() {
        HashSet<MapObject> set = new HashSet<>();
        for (MapObject[] objs : map)
            for (MapObject obj : objs)
                set.add(obj);
        return set;
    }
}