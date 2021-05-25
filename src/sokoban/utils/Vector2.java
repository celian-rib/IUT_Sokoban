//-------------------------------------------//
//             Célian Riboulet               //
//                                           //
//                Sokoban                    //
//                05/2021                    //
//                                           //
//              ProgOO / S2A"                //
//-------------------------------------------//

package sokoban.utils;

import java.util.HashMap;

public class Vector2 {

    /**
     * Up directional vector
     */
    public final static Vector2 UP = new Vector2(0, -1);

    /**
     * Down directional vector
     */
    public final static Vector2 DOWN = new Vector2(0, 1);

    /**
     * Left directional vector
     */
    public final static Vector2 LEFT = new Vector2(-1, 0);

    /**
     * Right directional vector
     */
    public final static Vector2 RIGHT = new Vector2(1, 0);

    /**
     * Relation betweens chars and directional vectors
     */
    public final static HashMap<Character, Vector2> CHAR_DIRECTION =
            new HashMap<Character, Vector2>() {
                {
                    put('U', UP);
                    put('D', DOWN);
                    put('L', LEFT);
                    put('R', RIGHT);
                }
            };

    /**
     * Actual x coordinate of this vector
     */
    public final int x;

    /**
     * Actual y coordinate of this vector
     */
    public final int y;

    /**
     * Create a new vector from coordinates
     * 
     * @param x x coordinate
     * @param y y coordinate
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Add a vector to this vector
     * 
     * @param vector vector to add with
     * @return addition of the two vectors
     */
    public Vector2 add(Vector2 vector) {
        return new Vector2(x + vector.x, y + vector.y);
    }

    @Override
    public String toString() {
        return "(" + x + ";" + y + ")";
    }
}
