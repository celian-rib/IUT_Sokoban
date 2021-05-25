package sokoban.map.builder;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapObject.MapObject;

public interface MapBuilder {

    /**
     * Create map data content
     * @return map 2d array f map object
     */
    MapObject[][] build() throws BuilderException; 
}
