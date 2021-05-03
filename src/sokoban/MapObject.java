package sokoban;

public abstract class MapObject {
    private final char CHARACTER;
    private final Vector2 position;

    public MapObject(Vector2 position, char c) {
        this.position = position;
        this.CHARACTER = c;
    }

    public MapObject(int x, int y, char c) {
        this.position = new Vector2(x, y);
        this.CHARACTER = c;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void draw() {
        System.out.print(CHARACTER);
    }

    @Override
    public String toString() {
        return CHARACTER + "";
    }
}
