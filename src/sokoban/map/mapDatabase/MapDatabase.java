package sokoban.map.mapDatabase;

import java.sql.*;
public final class MapDatabase implements AutoCloseable {
    
    private final static String DB_DILE_PATH = "MapDatabase.sqlite3";
    private final static String DB_URL = "jdbc:sqlite:" + DB_DILE_PATH;

    private final Connection conn;

    private static void loadSQLitePilots() {
        String sqlite_driver = "org.sqlite.JDBC";
        try {
            Class.forName(sqlite_driver);
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
            System.exit(1);
        }
    }

    public MapDatabase() throws SQLException {
        loadSQLitePilots();

        Connection conn = DriverManager.getConnection(DB_URL);
        this.conn = conn;
    }

    public Connection getDbConnection() {
        return conn;
    }

    public void initDb() {
        String sql = "" 
        + "create table MAPS (id int not null, name string not null, difficulty string not null)"
        + "create table ROWS (boardId int not null, rowId int not null, content string not null)";

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeQuery();
        } catch (SQLException e) {
            System.err.println(e);
        }

        System.out.println("Dabate initiated");
    }

    @Override
    public void close() throws Exception {
        conn.close();
    };
}
