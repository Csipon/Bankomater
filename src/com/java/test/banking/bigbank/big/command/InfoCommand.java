package command;

import database.MySqlConnector;
import main.CashMachine;
import main.ConsoleHelper;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

class InfoCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en", Locale.ENGLISH);
    private MySqlConnector connector = new MySqlConnector();

    @Override
    public void execute() throws SQLException, ClassNotFoundException {
        String[] currencyCode = new String[]{"usd", "uah", "eur"};
        String select = "SELECT id, value, count FROM ";
        boolean money = false;

        Connection db = connector.getConnection();

        for (int i = 0 ; i < currencyCode.length; i++) {
            ResultSet result = db.prepareStatement(select + currencyCode[i]).executeQuery();
            int allAmount = 0;
            while (result.next()) {
                allAmount += result.getInt("value") * result.getInt("count");
            }
            if (allAmount > 0){
                ConsoleHelper.writeMessage(res.getString("before")+ " "
                        + currencyCode[i].toUpperCase() + " - " + allAmount);
                money = true; // true if allAmount > 0
            }
        }

        if (!money)ConsoleHelper.writeMessage(res.getString("no.money"));

        db.close();
    }
}

