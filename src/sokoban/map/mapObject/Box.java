//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.map.mapObject;

import sokoban.utils.Vector2;

/**
 * Box mapObject, this object is moovable on the map
 */
public class Box extends MovableObject {

    public Box(int x, int y) {
        super(x, y, MapObject.ObjectType.BOX);
    }

    public Box(Vector2 position, boolean isOnDestination) {
        super(position, MapObject.ObjectType.BOX);
        this.isOnDestination = isOnDestination;
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) {
        return new Box(position, isOnDestination);
    }
}
