package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by HOUSE on 12.07.2016.
 */

public class MySqlConnector  {

    private static final String NAME = "root";
    private static final String PASSWORD = "wsgf1996";
    private static final String URL = "jdbc:mysql://localhost:3306/oxeygen";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public Connection getConnection () {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, NAME, PASSWORD);
        } catch (SQLException e) {
        }
        return  connection;
    }

}
