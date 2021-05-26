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
 * Destination mapObject
 */
public class Destination extends MapObject {

    public Destination(int x, int y) {
        super(x, y, MapObject.ObjectType.DESTINATION);
    }

    public Destination(Vector2 position) {
        super(position, MapObject.ObjectType.DESTINATION);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) {
        return new Destination(position);
    }
}
