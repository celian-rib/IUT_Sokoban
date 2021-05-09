package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Box extends MapObject implements MoovableObject {
    public Box(int x, int y) {
        super(x, y, MapObject.ObjectType.BOX);
    }

    public Box(Vector2 position) {
        super(position, MapObject.ObjectType.BOX);
    }

    @Override
    public MapObject getMapObject() {
        return this;
    }

    @Override
    public void moov(Vector2 direction, Map map)  throws Exception {
        if(!map.handleMoovPosibility(this, direction)) // This is not necessary as a box is mooved only if it can but we are never safe out there in the wild
            throw new InvalidMoovException("This moov is impossible");
        map.hardSwapObjects(position, direction);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Box(position);
    }
}
