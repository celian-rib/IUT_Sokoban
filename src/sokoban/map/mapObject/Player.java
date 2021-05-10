package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Player extends MoovableObject {

    public Player(int x, int y) {
        super(x, y, MapObject.ObjectType.PLAYER);
    }

    public Player(Vector2 position, boolean isOnDestination) {
        super(position, MapObject.ObjectType.PLAYER);
        this.isOnDestination = isOnDestination;
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Player(position, isOnDestination);
    }
}
