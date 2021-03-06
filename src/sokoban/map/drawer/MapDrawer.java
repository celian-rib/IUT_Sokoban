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
 * Draw a map
 */
public interface MapDrawer {

    /**
     * Draw a map data array 
     * @param map as a 2d array
     */
    public void draw(MapObject[][] map);
}
