package sokoban.map.mapObject;

import java.util.HashMap;

import sokoban.Vector2;

public abstract class MapObject {

    public static enum ObjectType {
        EMPTY,
        WALL,
        BOX,
        DESTINATION,
        PLAYER,
    }
    public static HashMap<ObjectType, Character> typeCharacters = new HashMap<ObjectType, Character>(){{
        put(ObjectType.EMPTY, ' ');
        put(ObjectType.WALL, '#');
        put(ObjectType.BOX, 'C');
        put(ObjectType.DESTINATION, 'x');
        put(ObjectType.PLAYER, 'P');
    }};
    // public static HashMap<ObjectType, Character> typeCharacters = new HashMap<ObjectType, Character>(){{
    //     put(ObjectType.EMPTY, ' ');
    //     put(ObjectType.WALL, '▓');
    //     put(ObjectType.BOX, 'C');
    //     put(ObjectType.DESTINATION, 'x');
    //     put(ObjectType.PLAYER, '웃');
    // }};

    public final ObjectType TYPE;
    protected final Vector2 position;

    public MapObject(Vector2 position, ObjectType type) {
        this.position = position;
        this.TYPE = type;
    }

    public MapObject(int x, int y, ObjectType type) {
        this.position = new Vector2(x, y);
        this.TYPE = type;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void draw() {
        System.out.print(typeCharacters.get(TYPE));
    }

    @Override
    public String toString() {
        return position.toString() + "  [" + typeCharacters.get(TYPE) + "]";
    }

    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        throw new UnsupportedOperationException("Impossible copy on object of type " + TYPE);
    }
}
