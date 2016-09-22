package database;

import java.sql.*;

public class WriteTable {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        MySqlConnector connector = new MySqlConnector();

        Connection db = connector.getConnection();
        System.out.println("Соединение установлено");
        Statement st = db.createStatement();

        ResultSet result = st.executeQuery("SELECT * FROM usd ");

        while (result.next()){
            System.out.println("Номер в базе # " + result.getInt("id") + " - "
                    + result.getInt("value") + " - " + result.getInt("count"));
        }
    }
}
