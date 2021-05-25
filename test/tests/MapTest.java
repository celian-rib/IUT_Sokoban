//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import sokoban.map.Map;
import sokoban.map.mapObject.MapObject;
import sokoban.map.drawer.LowDetailsMapDrawer;
import sokoban.map.builder.MapFromFileBuilder;
import static org.junit.Assert.*;
import org.junit.Test;
import sokoban.exceptions.*;
import sokoban.map.mapObject.Box;
import sokoban.map.mapObject.Player;
import sokoban.utils.Vector2;

public class MapTest {

    private Map getTestMap(String path) {
        try {
            return new Map(new LowDetailsMapDrawer(), new MapFromFileBuilder(path));
        } catch (BuilderException er) {
            System.err.println(er);
        }
        return null;
    }

    private Map getTestMap() {
        return getTestMap("SokobanMaps/push__easy.txt");
    }

    @Test
    public void testMoveObject() throws Exception {
        Map map = getTestMap("SokobanMaps/tutorial__easy.txt");
        assertNotEquals(map, null);

        Player p = map.getMapPlayer();
        assertEquals(p.getPosition().x, 3);
        assertEquals(p.getPosition().y, 1);

        map.moveObject(p, Vector2.DOWN);
        p = map.getMapPlayer();
        assertEquals(p.getPosition().x, 3);
        assertEquals(p.getPosition().y, 2);

        for (int x = 4; x < 5; x++) {
            map.moveObject(p, Vector2.RIGHT);
            p = map.getMapPlayer();
            assertEquals(p.getPosition().x, x);
            assertEquals(p.getPosition().y, 2);
        }
    }

    @Test
    public void testHasEmptyDestination() throws InvalidPositionException {
        Map map = getTestMap("SokobanMaps/tutorial__easy.txt");
        assertNotEquals(map, null);

        assertTrue(map.hasEmptyDestination());

        map.setObjectOnMap(new Box(10, 2));

        assertFalse(map.hasEmptyDestination());
    }

    @Test
    public void testGetMapPlayer() {
        Map map = getTestMap();
        assertNotEquals(map, null);

        Player p = map.getMapPlayer();
        assertNotEquals(p, null);
        assertEquals(p.TYPE, MapObject.ObjectType.PLAYER);

        assertEquals(p.getPosition().x, 5);
        assertEquals(p.getPosition().y, 2);

        assertFalse(p.isOnDestination());
    }
}
