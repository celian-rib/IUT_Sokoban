package sokoban.map.mapObject;

import sokoban.exceptions.InvalidMoveException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;
import sokoban.utils.Vector2;

/**
 * MapObject that can be moovables Eg : a player or a box
 */
public abstract class MovableObject extends MapObject {

    /**
     * Determine if this moovable object is on position that was originaly a MapObject of type
     * DESTINATION If so we store with this boolean the fact that a destination will have to be
     * placed at this object position when it will moov to another position.
     */
    protected boolean isOnDestination = false;

    /**
     * Create a new moovable MapObject
     * 
     * @param position position of the object
     * @param type type of the object
     */
    public MovableObject(Vector2 position, ObjectType type) {
        super(position, type);
    }

    /**
     * Create a new moovable MapObject
     * 
     * @param x the x position
     * @param y the y position
     * @param type the type of the object
     */
    public MovableObject(int x, int y, ObjectType type) {
        super(x, y, type);
    }

    /**
     * Determine if this moovable object is on position that was originaly a MapObject of type
     * DESTINATION If so we store with this boolean the fact that a destination will have to be
     * placed at this object position when it will moov to another position.
     */
    public boolean isOnDestination() {
        return isOnDestination;
    }

    public void setIsOnDestination(boolean value) {
        this.isOnDestination = value;
    }

    /**
     * Moov this object in a specific direction
     * 
     * @param direction vector that represent the movment of the object (Normalized vectors are used
     *        for directions, but non-normalized are working as well)
     * @param map map on which the object is present
     * @throws InvalidMoveException
     * @throws InvalidPositionException
     * @throws UnsupportedOperationException
     */
    public void move(Vector2 direction, Map map) throws InvalidMoveException, InvalidPositionException, UnsupportedOperationException {
        map.moveObject(this, direction);
    }
}
