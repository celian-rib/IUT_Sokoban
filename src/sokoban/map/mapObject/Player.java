package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Player extends MapObject implements MoovableObject {
    public Player(int x, int y) {
        super(x, y, MapObject.ObjectType.PLAYER);
    }

    public Player(Vector2 position) {
        super(position, MapObject.ObjectType.PLAYER);
    }

    @Override
    public MapObject getMapObject() {
        return this;
    }

    @Override
    public void moov(Vector2 direction, Map map) throws Exception {
        if(!map.moovAllowed(this, direction))
            throw new InvalidMoovException("This moov is impossible");
        map.hardSwapObjects(position, direction);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Player(position);
    }
}
