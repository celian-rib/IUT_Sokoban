/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.sql.SQLException;
import static org.junit.Assert.*;
import org.junit.Test;
import sokoban.exceptions.BuilderException;
import sokoban.map.builder.MapBuilder;
import sokoban.map.builder.MapFromDatabaseBuilder;
import sokoban.map.builder.MapFromFileBuilder;
import sokoban.map.mapDatabase.MapDatabase;
import sokoban.map.mapObject.MapObject;

public class BuilderTest {

    @Test
    public void testBuildFromFile() throws BuilderException {
        testBuilder(new MapFromFileBuilder("SokobanMaps/push__easy.txt"));
    }

    @Test
    public void testBuildFromDatabase() throws SQLException, BuilderException {
        MapDatabase db = new MapDatabase();
        testBuilder(new MapFromDatabaseBuilder(1, db));
    }
    
    private void testBuilder(MapBuilder builder) throws BuilderException {
        MapObject[][] map = builder.build();

        assertNotEquals(map, null);
        
        //Test map size
        assertEquals(7, map.length);
        for (int i = 0; i < map.length; i++) {
            assertEquals(12, map[i].length);
        }
        
        // Check player position
        assertEquals(MapObject.ObjectType.PLAYER, map[2][5].TYPE);
        
        // Check box position
        assertEquals(MapObject.ObjectType.BOX, map[2][4].TYPE);
        
        // Check destination position
        assertEquals(MapObject.ObjectType.DESTINATION, map[5][5].TYPE);
    }
}
