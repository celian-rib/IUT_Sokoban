package sokoban.map.builder;

import sokoban.exceptions.BuilderException;
import sokoban.map.mapObject.MapObject;

public interface MapBuilder {
    MapObject[][] build() throws BuilderException; 
}
