package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;

/**
 * MapObject that can be moovables Eg : a player or a box
 */
public abstract class MoovableObject extends MapObject {

    /**
     * Determine if this moovable object is on position that was originaly a
     * MapObject of type DESTINATION If so we store with this boolean the fact that
     * a destination will have to be placed at this object position when it will
     * moov to another position.
     */
    protected boolean isOnDestination = false;

    /**
     * Create a new moovable MapObject
     * 
     * @param position position of the object
     * @param type     type of the object
     */
    public MoovableObject(Vector2 position, ObjectType type) {
        super(position, type);
    }

    /**
     * Create a new moovable MapObject
     * 
     * @param x    the x position
     * @param y    the y position
     * @param type the type of the object
     */
    public MoovableObject(int x, int y, ObjectType type) {
        super(x, y, type);
    }

    /**
     * Determine if this moovable object is on position that was originaly a
     * MapObject of type DESTINATION If so we store with this boolean the fact that
     * a destination will have to be placed at this object position when it will
     * moov to another position.
     */
    public boolean isOnDestination() {
        return isOnDestination;
    }

    /**
     * Moov this object in a specific direction
     * 
     * @param direction vector that represent the movment of the object (Normalized
     *                  vectors are used for directions, but non-normalized are
     *                  working as well)
     * @param map       map on which the object is present
     * @throws InvalidMoovException
     * @throws InvalidPositionException
     * @throws UnsupportedOperationException
     */
    public void moov(Vector2 direction, Map map)
            throws InvalidMoovException, InvalidPositionException, UnsupportedOperationException {
        if (!map.handleMoovPosibility(this, direction))
            throw new InvalidMoovException("You can't go in this direction...");

        Vector2 oldDestinationPosition = null;

        if (isOnDestination) {
            oldDestinationPosition = new Vector2(position.x, position.y);
            isOnDestination = false;
        }

        if (map.getObjectAtPosition(position.add(direction)).TYPE == MapObject.ObjectType.DESTINATION) {
            isOnDestination = true;
            map.setObjectOnMap(createPositionedCopy(position.add(direction)));
            map.setObjectOnMap(new Empty(position));
        } else {
            map.hardSwapObjects(position, direction);
        }

        if (oldDestinationPosition != null)
            map.setObjectOnMap(new Destination(oldDestinationPosition));
    }
}
