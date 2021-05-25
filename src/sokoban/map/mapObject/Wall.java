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

public class Wall extends MapObject {

    public Wall(int x, int y) {
        super(x, y, MapObject.ObjectType.WALL);
    }

    @Override
    public MapObject createPositionedCopy(Vector2 position) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Error : Impossible copy on object of type " + TYPE);
    }
}
