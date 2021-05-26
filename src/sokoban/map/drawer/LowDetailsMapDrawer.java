//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.map.drawer;

import sokoban.map.mapObject.MapObject;

/**
 * Draw the map simply (One char = one object)
 */
public class LowDetailsMapDrawer implements MapDrawer {

    @Override
    public void draw(MapObject[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++)
                map[i][j].draw();
            System.out.println();
        }
    }
}
