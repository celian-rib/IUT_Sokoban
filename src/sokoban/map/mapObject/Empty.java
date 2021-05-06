package sokoban.map.mapObject;

public class Empty extends MapObject {
    public Empty(int x, int y) {
        super(x, y, MapObject.ObjectType.EMPTY);
    }
}
