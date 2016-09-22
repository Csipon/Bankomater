package command;

import database.MySqlConnector;
import exception.InterruptOperationException;
import exception.NotEnoughMoneyException;
import main.CashMachine;
import main.ConsoleHelper;
import main.CurrencyManipulator;
import main.CurrencyManipulatorFactory;

import java.sql.*;
import java.util.ConcurrentModificationException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


class WithdrawCommand implements Command
{

    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en", Locale.ENGLISH);
    private MySqlConnector conectore = new MySqlConnector();
    private String selectAll = "SELECT id, value, count FROM ";
    private String update;

    @Override
    public void execute() throws InterruptOperationException, ClassNotFoundException, SQLException {

    int withdrawAmount;

    String currencyCode = ConsoleHelper.askCurrencyCode();

    update = "UPDATE " + currencyCode.toLowerCase() + " SET count = ? where value = ?";
    CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);

    Connection db = conectore.getConnection();
    PreparedStatement preparedStatement = db.prepareStatement(selectAll + currencyCode);

    ResultSet result = preparedStatement.executeQuery();
        while (result.next()){
            manipulator.addAmount(result.getInt("value"), result.getInt("count"));
        }

    ConsoleHelper.writeMessage(res.getString("before"));
    while (true)
    {
        try
        {
            String amount = ConsoleHelper.readString();
            withdrawAmount = Integer.parseInt(amount);
            if (withdrawAmount <= 0)
            {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                continue;
            }
        }
        catch (NumberFormatException e)
        {
            ConsoleHelper.writeMessage(res.getString("specify.amount"));
            continue;
        }

//        if ATM has money we need
        if (!manipulator.isAmountAvailable(withdrawAmount))
        {
            ConsoleHelper.writeMessage(res.getString("not.enough.money"));
            continue;
        }

        try
        {
            int summary = 0;

            Map<Integer, Integer> withdrawMoney = manipulator.withdrawAmount(withdrawAmount);

            for (Map.Entry<Integer, Integer> maps : withdrawMoney.entrySet())
            {
                 summary += maps.getKey() * maps.getValue();
            }



            Map<Integer, Integer> resultMoney = manipulator.getDenominations();
            PreparedStatement prForUpdate = db.prepareStatement(update);
            for (Map.Entry<Integer, Integer> m : resultMoney.entrySet()){
                prForUpdate.setInt(1, m.getValue());
                prForUpdate.setInt(2, m.getKey());
                prForUpdate.executeUpdate();
            }

            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), summary, currencyCode));


            preparedStatement.close();
            prForUpdate.close();
            db.close();
            break;
        }catch (NotEnoughMoneyException e)
        {
            ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
        }catch (ConcurrentModificationException a){
        }
    }
}
}
