package sokoban.map.mapObject;

import sokoban.Vector2;
import sokoban.map.Map;

public interface MoovableObject {
    public MapObject getMapObject();

    public void moov(Vector2 direction, Map map) throws Exception;
}
