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
 * Empty map object, used to represent an empty cell
 */
public class Empty extends MapObject {

    public Empty(int x, int y) {
        super(x, y, MapObject.ObjectType.EMPTY);
    }
   
    public Empty(Vector2 position) {
        super(position, MapObject.ObjectType.EMPTY);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) {
        return new Empty(position);
    }
}
