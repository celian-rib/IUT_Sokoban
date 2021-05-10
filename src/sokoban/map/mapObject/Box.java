package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Box extends MoovableObject {

    public Box(int x, int y) {
        super(x, y, MapObject.ObjectType.BOX);
    }

    public Box(Vector2 position, boolean isOnDestination) {
        super(position, MapObject.ObjectType.BOX);
        this.isOnDestination = isOnDestination;
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Box(position, isOnDestination);
    }
}
