package sokoban;

public class Map {
    private MapObject[][] map;

    public Map(MapBuilder builder) throws BuilderException {
        this.map = builder.build();
    }

    public void draw() {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == null) {
                    System.out.print('.');
                } else {
                    map[i][j].draw();
                }
            }
            System.out.println();
        }
    }

    public MapObject addWall(Vector2 position) {
        return null;
    }

    public MapObject addBox(Vector2 position) {
        return null;
    }

    public MapObject addEmpty(Vector2 position) {
        return null;
    }

    public MapObject addDestination(Vector2 position) {
        return null;
    }
}
