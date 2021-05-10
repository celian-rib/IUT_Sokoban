package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.exceptions.InvalidMoovException;
import sokoban.exceptions.InvalidPositionException;
import sokoban.map.Map;
import sokoban.map.mapObject.MapObject.ObjectType;

public class Player extends MapObject implements MoovableObject {
    private boolean isOnDestination = false;

    public Player(int x, int y) {
        super(x, y, MapObject.ObjectType.PLAYER);
    }

    public Player(Vector2 position, boolean isOnDestination) {
        super(position, MapObject.ObjectType.PLAYER);
        this.isOnDestination = isOnDestination;
    }

    @Override
    public MapObject getMapObject() {
        return this;
    }

    @Override
    public void moov(Vector2 direction, Map map) throws Exception {
        if(!map.handleMoovPosibility(this, direction))
            throw new InvalidMoovException("This moov is impossible");
        
        Vector2 oldDestinationPosition = null;

        if(isOnDestination){
            oldDestinationPosition = new Vector2(position.x, position.y);
            isOnDestination = false;
        }
            
        if(map.getObjectAtPosition(position.add(direction)).TYPE == MapObject.ObjectType.DESTINATION){
            isOnDestination = true;
            map.setObjectOnMap(createPositionedCopy(position.add(direction)));
            map.setObjectOnMap(new Empty(position));
        } else {
            map.hardSwapObjects(position, direction);
        }

        if(oldDestinationPosition != null)
            map.setObjectOnMap(new Destination(oldDestinationPosition));
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws Exception {
        return new Player(position, isOnDestination);
    }
}
