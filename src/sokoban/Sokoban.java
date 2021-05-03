package sokoban;

/**
 *
 * @author celia
 */
public class Sokoban {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MapFromFileBuilder builder = new MapFromFileBuilder("C:\\Users\\celia\\Desktop\\sokoban\\MapFile.txt");
        
        try {
            Map map = new Map(builder);
            map.draw();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
