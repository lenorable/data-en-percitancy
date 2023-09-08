package nl.hu.dp.p1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connect {
    static Connection connection = null;

    static String username = "postgres";
    static String password = "k6LfYEIszD1cOP29qTvx";

    public Connection getCon() throws SQLException {
        if (connection == null) {

            String URL = "jdbc:postgresql://localhost:5432/ovchip";

            connection = DriverManager.getConnection(URL, username, password);
        }

        return connection;
    }

    public void closeCon() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
