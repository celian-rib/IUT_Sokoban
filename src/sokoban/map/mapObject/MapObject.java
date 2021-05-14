package sokoban.map.mapObject;

import java.util.HashMap;
import sokoban.Vector2;

/**
 * Object that can be placed on a map Each object has basically a type and a
 * position
 */
public abstract class MapObject {

    /**
     * All possibles object types
     */
    public static enum ObjectType {
        EMPTY, WALL, BOX, DESTINATION, PLAYER,
    }

    /**
     * Characters defining a specific Object type Used for the low details
     * representation of the game
     */
    public static HashMap<ObjectType, Character> typeCharacters = new HashMap<ObjectType, Character>() {
        {
            put(ObjectType.EMPTY, ' ');
            put(ObjectType.WALL, '#');
            put(ObjectType.BOX, 'C');
            put(ObjectType.DESTINATION, 'x');
            put(ObjectType.PLAYER, 'P');
        }
    };

    public boolean isEmpty() {
        return this.TYPE == ObjectType.EMPTY;
    };

    public boolean isWall() {
        return this.TYPE == ObjectType.WALL;
    };

    public boolean isBox() {
        return this.TYPE == ObjectType.BOX;
    };

    public boolean isDestination() {
        return this.TYPE == ObjectType.DESTINATION;
    };

    public boolean isPlayer() {
        return this.TYPE == ObjectType.PLAYER;
    };

    /**
     * The type of this object
     */
    public final ObjectType TYPE;

    /**
     * The position of this onbject on the map This position is freezed and so an
     * object is not able to be mooved directly on the map
     * 
     * @see createPositionedCopy() To change the position of an object
     */
    protected final Vector2 position;

    /**
     * Create a new object of a specific type at a specific position
     * 
     * @param position position as a vector for the object
     * @param type     of the object
     */
    public MapObject(Vector2 position, ObjectType type) {
        this.position = position;
        this.TYPE = type;
    }

    /**
     * Create a new object of a specific type at a specific position
     * 
     * @param x    x position
     * @param y    y position
     * @param type type of the object
     */
    public MapObject(int x, int y, ObjectType type) {
        this.position = new Vector2(x, y);
        this.TYPE = type;
    }

    /**
     * @return the position of this object
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Draw this object with its typeCharacter with no line ending
     */
    public void draw() {
        System.out.print(typeCharacters.get(TYPE));
    }

    @Override
    public String toString() {
        return position.toString() + "  [" + typeCharacters.get(TYPE) + "]";
    }

    /**
     * Create a copy of this object at a specific position on the map
     * 
     * @param position position of the copy
     * @return the new create object (the copy)
     * @throws UnsupportedOperationException
     */
    public MapObject createPositionedCopy(Vector2 position) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Error : Impossible copy on object of type " + TYPE);
    }
}
