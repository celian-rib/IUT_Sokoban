/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sokoban.map.drawer;

import sokoban.map.mapObject.MapObject;

public interface MapDrawer {
    public void draw(MapObject[][] map);
}
