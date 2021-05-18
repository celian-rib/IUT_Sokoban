package sokoban.map.mapObject;

public class Wall extends MapObject {

    public Wall(int x, int y) {
        super(x, y, MapObject.ObjectType.WALL);
    }

}
