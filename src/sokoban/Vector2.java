package sokoban;

import java.util.HashMap;

public class Vector2 {
    public final static Vector2 UP = new Vector2(0, -1);
    public final static Vector2 DOWN = new Vector2(0, 1);
    public final static Vector2 LEFT = new Vector2(-1, 0);
    public final static Vector2 RIGHT = new Vector2(1, 0);

    public final static HashMap<Character, Vector2> CHAR_DIRECTION = new HashMap<Character, Vector2>(){{
        put('U', UP);
        put('D', DOWN);
        put('L', LEFT);
        put('R', RIGHT);
    }};

    public final int x;
    public final int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 vector) {
        return new Vector2(x + vector.x, y + vector.y);
    }

    @Override
    public String toString() {
        return "("+x+";"+y+")";
    }
}
