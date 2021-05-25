package sokoban.map.mapDatabase;

import java.sql.*;
import java.util.ArrayList;

public class MapDatabase implements AutoCloseable {

    /**
     * Class representing one map objct in the databse's MPAS table
     */
    public class Map {
        public final int id;
        public final String name;
        public final String difficulty;

        /**
         * Sql query to create the maps table
         */
        public static final String createSql =
                "create table MAPS (id int not null, name string not null, difficulty string not null);";

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

    /**
     * Class representing on row in the database's ROWS table
     */
    public static class Row {
        public final int mapId;
        public final int rowId;
        public final String content;

        /**
         * Sql query to create the rows table
         */
        public static final String createSql =
                "create table ROWS (mapId int not null, rowId int not null, content string not null);";

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

    /**
     * Create a new MapDatabase instance that handle its own connection
     */
    public MapDatabase() throws SQLException {
        loadSQLitePilots();
        this.conn =  DriverManager.getConnection(DB_URL);
    }

    /**
     * Load pilot to establisg connection with the database
     */
    private static void loadSQLitePilots() {
        String sqlite_driver = "org.sqlite.JDBC";
        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    /**
     * @return the actual connection with the database
     */
    public Connection getDbConnection() {
        return conn;
    }

    /**
     * Calculate the smallest map id that is available in the database
     * 
     * @return map id available
     * @throws SQLException
     */
    public int getAvailableMapId() throws SQLException {
        int max = 0;
        for (Map m : getMaps()) {
            if (m.id >= max)
                max = m.id + 1;
        }
        return max;
    }

    /**
     * Add a map to the database
     * 
     * @param map map to add
     * @param rows list of rows attached to this map
     * @throws SQLException
     */
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

    /**
     * Remove a map from the database
     * This handle removing all the data from all the tables related to this map
     * @param mapId id of the map to delete
     */
    public void removeMap(int mapId) {
        String sql = "delete from MAPS where id = " + mapId;
        updateQuery(sql);
        sql = "delete from ROWS where mapId = " + mapId;
        updateQuery(sql);
        System.out.println("Map deleted");
    }

    /**
     * @return all the maps in the database
     * @throws SQLException
     */
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

    /**
     * @return all the rows in the database
     * @throws SQLException
     */
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

    /**
     * Drop the Map and Row tables
     */
    public void dropTables() {
        updateQuery("drop table if exists MAPS");
        updateQuery("drop table if exists ROWS");
    }

    /**
     * Initalize the database with the Map and Row tables
     */
    public void initDb() {
        updateQuery(Map.createSql);
        updateQuery(Row.createSql);
    }

    /**
     * Execute an update query
     * 
     * @param sql sql query string
     */
    private void updateQuery(String sql) {
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    /**
     * Close the connection
     */
    @Override
    public void close() throws Exception {
        conn.close();
    };
}
