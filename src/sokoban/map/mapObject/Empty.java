package sokoban.map.mapObject;

import sokoban.Vector2;

public class Empty extends MapObject {
    public Empty(int x, int y) {
        super(x, y, MapObject.ObjectType.EMPTY);
    }
   
    public Empty(Vector2 position) {
        super(position, MapObject.ObjectType.EMPTY);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Empty(position);
    }
}
