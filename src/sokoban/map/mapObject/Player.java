package sokoban.map.mapObject;

import sokoban.utils.Vector2;

public class Player extends MovableObject {

    public Player(int x, int y) {
        super(x, y, MapObject.ObjectType.PLAYER);
    }

    public Player(Vector2 position, boolean isOnDestination) {
        super(position, MapObject.ObjectType.PLAYER);
        this.isOnDestination = isOnDestination;
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) {
        return new Player(position, isOnDestination);
    }
}
