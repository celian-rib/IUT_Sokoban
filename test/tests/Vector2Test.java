/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import sokoban.utils.Vector2;

public class Vector2Test {
    
    @Test
    public void testConstructor() {
        Vector2 vec = new Vector2(404, 102);
        assertEquals(vec.x, 404);
        assertEquals(vec.y, 102);
    }
    
    @Test
    public void testAdd() {
        Vector2 vec = new Vector2(404, 102);
        assertEquals(vec.x, 404);
        assertEquals(vec.y, 102);
        
        vec = vec.add(new Vector2(-10, 38));
        assertEquals(vec.x, 394);
        assertEquals(vec.y, 140);
    }
}
