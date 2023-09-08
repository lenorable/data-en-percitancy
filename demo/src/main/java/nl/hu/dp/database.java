package nl.hu.dp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    static Connection connection = null;

    static String username = "postgres";
    static String password = "k6LfYEIszD1cOP29qTvx";

    private static Connection getCon() throws SQLException {
        if (connection == null) {

            String URL = "jdbc:postgresql://localhost:5432/data-percistancy";

            connection = DriverManager.getConnection(URL, username, password);
        }

        return connection;
    }

    private static void closeCon() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }

    public static void testConnection() throws SQLException {
        getCon();
        System.out.println(connection);
    }
}
