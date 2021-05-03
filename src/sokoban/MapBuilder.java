package sokoban;

public interface MapBuilder {
    MapObject[][] build() throws BuilderException; 
}
