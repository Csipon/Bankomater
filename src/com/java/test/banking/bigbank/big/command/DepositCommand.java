package command;

import database.MySqlConnector;
import main.CashMachine;
import main.ConsoleHelper;

import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

class DepositCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en", Locale.ENGLISH);
    private MySqlConnector connector = new MySqlConnector();
    private String select;

    @Override
    public void execute() throws ClassNotFoundException, SQLException {
        Connection db = connector.getConnection();
        ConsoleHelper.writeMessage(res.getString("before"));
        while (true)
        {
            try
            {
                String curCode = ConsoleHelper.askCurrencyCode(); // ask Operation usd, uah, eur...

                select = "SELECT count FROM " + curCode +" WHERE value = ?";

                PreparedStatement prState = db.prepareStatement(select);
                String[] str = ConsoleHelper.getValidTwoDigits(curCode);

                ResultSet rs = prState.executeQuery();

                String update = "UPDATE " + curCode.toLowerCase() + " SET count = ? where value = ?";
                PreparedStatement prUpdate = db.prepareStatement(update);
                prUpdate.setInt(1, Integer.parseInt(str[1]) + rs.getInt(1));
                prUpdate.setInt(2, Integer.parseInt(str[0]));

                ConsoleHelper.writeMessage(String.format(res.getString("success.format"),
                        (Integer.parseInt(str[0]) * Integer.parseInt(str[1])), curCode));

                prState.close();
                prUpdate.close();
                db.close();
                break;
            }
            catch (Exception e)
            {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
                continue;
            }
        }
    }
}
