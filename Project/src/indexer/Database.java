package indexer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.Statement;

public class Database {
    public static void connect() {
        Connection conn = null;
        try {
            // db parameters
            String url = "jdbc:sqlite:Test.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:E:\\UNIVERSITY\\Semester 8\\Advanced Programming Techniques\\Project\\SearchEngine\\Project\\src\\indexer\\" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:E:\\UNIVERSITY\\Semester 8\\Advanced Programming Techniques\\Project\\SearchEngine\\Project\\src\\indexer\\test.db";

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS words (\n"
                + "	words text NOT NULL UNIQUE,\n"
                + "	TF integer NOT NULL,\n"
                + "	PRIMARY KEY(words)\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
