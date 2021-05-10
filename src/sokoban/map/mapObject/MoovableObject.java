package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;

public abstract class MoovableObject extends MapObject {
    protected boolean isOnDestination = false;
    
    public MoovableObject(Vector2 position, ObjectType type) {
        super(position, type);
    }

    public MoovableObject(int x, int y, ObjectType type) {
        super(x, y, type);
    }

    public boolean isOnDestination() {
        return isOnDestination;
    }

    public void moov(Vector2 direction, Map map) throws InvalidMoovException, InvalidPositionException, UnsupportedOperationException {
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
