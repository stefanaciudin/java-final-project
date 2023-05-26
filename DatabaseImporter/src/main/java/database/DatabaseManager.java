package database;

import java.sql.*;

/**
 * Class DatabaseManager - singleton class used in order to manage
 * the connection to the database
 */

public class DatabaseManager {
    private static final String URL="jdbc:mysql://localhost:3306/spotifydb";
    private static final String USER="root";
    private static final String PASSWORD="root";
    private static Connection connection = null;
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

}