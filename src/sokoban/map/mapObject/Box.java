package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Box extends MapObject implements MoovableObject {
    public Box(int x, int y) {
        super(x, y, MapObject.ObjectType.BOX);
    }

    @Override
    public MapObject getMapObject() {
        return this;
    }

    @Override
    public void moov(Vector2 direction, Map map)  throws InvalidMoovException {
        
    }
}
