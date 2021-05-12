package sokoban.map.mapDatabase;

import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class MapDatabase implements AutoCloseable {

    public class Map {
        public final int id;
        public final String name;
        public final String difficulty;

        public static final String createSql = "create table MAPS (id int not null, name string not null, difficulty string not null);";

        public Map(String name, String difficulty) throws SQLException {
            this.id = getAvailableMapId();
            this.name = name;
            this.difficulty = difficulty;
        }

        public Map(int id, String name, String difficulty) throws SQLException {
            this.id = id;
            this.name = name;
            this.difficulty = difficulty;
        }

        @Override
        public String toString() {
            return String.format("| %3h | %20s | %15s |", id, name, difficulty);
        }
    }

    public static class Row {
        public final int boardId;
        public final int rowId;
        public final String content;

        public static final String createSql = "create table ROWS (boardId int not null, rowId int not null, content string not null);";

        public Row(int boardId, int rowId, String content) {
            this.boardId = boardId;
            this.rowId = rowId;
            this.content = content;
        }

        @Override
        public String toString() {
            return String.format("| %3h | %20s | %50s |", boardId, rowId, content);
        }
    }

    private final static String DB_DILE_PATH = "MapDatabase.sqlite3";
    private final static String DB_URL = "jdbc:sqlite:" + DB_DILE_PATH;

    private final Connection conn;

    public MapDatabase() throws SQLException {
        loadSQLitePilots();

        Connection conn = DriverManager.getConnection(DB_URL);
        this.conn = conn;
    }

    private static void loadSQLitePilots() {
        String sqlite_driver = "org.sqlite.JDBC";
        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    public Connection getDbConnection() {
        return conn;
    }

    public int getAvailableMapId() throws SQLException {
        int max = 0;
        for (Map m : getMaps()) {
            if (m.id >= max)
                max = m.id + 1;
        }
        return max;
    }

    public void addMap(Map map, ArrayList<Row> rows) throws SQLException {
        String sql = "insert into Maps (id, name, difficulty) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, map.id);
        statement.setString(2, map.name);
        statement.setString(3, map.difficulty);
        statement.executeUpdate();
        System.out.println("Map " + map.name + " added to the database");
    }

    public ArrayList<Map> getMaps() throws SQLException {
        ArrayList<Map> maps = new ArrayList<Map>();
        String sql = "select * from Maps";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int id = results.getInt("id");
            String name = results.getString("name");
            String difficulty = results.getString("difficulty");
            maps.add(new Map(id, name, difficulty));
        }

        return maps;
    }

    public String mapTableString() {
        String result = String.format("| %3s | %20s | %15s |", "id", "name", "difficulty").replace(' ', '-') + "\n";
        try {
            for (Map m : getMaps())
                result += m.toString() + "\n";
            result += "------------------------------------------------";
            return result;
        } catch (Exception e) {
            return "Table error";
        }
    }

    public void initDb() {
        String sql = MapDatabase.Map.createSql + " " + MapDatabase.Row.createSql;
        System.out.println(sql);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
            System.out.println("Database successfully initiated");
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    public static ArrayList<Row> getRowsFromFile(int mapId, String filePath) throws FileNotFoundException {
        ArrayList<Row> rows = new ArrayList<Row>();
        Scanner scanner = new Scanner(new File(filePath));
        int i = 0;
        while (scanner.hasNextLine()) {
            rows.add(new Row(mapId, i, scanner.nextLine()));
            i++;
        }

        return rows;
    }

    @Override
    public void close() throws Exception {
        conn.close();
    };
}
