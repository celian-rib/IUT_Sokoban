package sokoban.map.mapDatabase;

import java.sql.*;
import java.util.ArrayList;

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
        public final int mapId;
        public final int rowId;
        public final String content;

        public static final String createSql = "create table ROWS (mapId int not null, rowId int not null, content string not null);";

        public Row(int mapId, int rowId, String content) {
            this.mapId = mapId;
            this.rowId = rowId;
            this.content = content;
        }

        @Override
        public String toString() {
            return String.format("| %6h | %6s | %30s |", mapId, rowId, content);
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
        String sql = "insert into MAPS (id, name, difficulty) values (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, map.id);
        statement.setString(2, map.name);
        statement.setString(3, map.difficulty);
        statement.executeUpdate();

        for (Row r : rows) {
            sql = "insert into ROWS (mapId, rowId, content) values (?,?,?)";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, r.mapId);
            statement.setInt(2, r.rowId);
            statement.setString(3, r.content);
            statement.executeUpdate();
        }

        System.out.println("Added to the database");
    }

    public ArrayList<Map> getMaps() throws SQLException {
        ArrayList<Map> maps = new ArrayList<Map>();
        String sql = "select * from MAPS";
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

    public ArrayList<Row> getRows() throws SQLException {
        ArrayList<Row> rows = new ArrayList<Row>();
        String sql = "select * from ROWS";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet results = statement.executeQuery();

        while (results.next()) {
            int mapId = results.getInt("mapId");
            int rowId = results.getInt("rowId");
            String content = results.getString("content");
            rows.add(new Row(mapId, rowId, content));
        }

        return rows;
    }

    public void dropTables() {
        updateQuery("drop table if exists MAPS");
        updateQuery("drop table if exists ROWS");
    }

    public void initDb() {
        updateQuery(Map.createSql);
        updateQuery(Row.createSql);
    }

    private void updateQuery(String sql) {
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Override
    public void close() throws Exception {
        conn.close();
    };
}
