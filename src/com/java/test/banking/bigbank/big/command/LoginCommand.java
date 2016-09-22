package command;

import exception.InterruptOperationException;
import main.CashMachine;
import main.ConsoleHelper;

import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command
{
    private ResourceBundle validCreditCards
            = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +  "verifiedCards", Locale.ENGLISH);
    private ResourceBundle res
            = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH +  "login_en", Locale.ENGLISH);

    @Override
    public void execute() throws InterruptOperationException, SQLException, ClassNotFoundException {
        String password;
        String numberCard;
        String pass;
        String card;

        boolean isValidData = false;
        int count = 0;
        ConsoleHelper.writeMessage(res.getString("before"));
        ConsoleHelper.writeMessage(res.getString("specify.data"));
        while (!isValidData){
            Enumeration bundleKeys = validCreditCards.getKeys();
            if (count > 0)
            {
                ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
            }
            count++;
            card = ConsoleHelper.readString();
            pass = ConsoleHelper.readString();
            if (card.length() == 12 && pass.length() == 4 && card.matches("\\d{12}") && pass.matches("\\d{4}")){
                while (bundleKeys.hasMoreElements())
                {
                    numberCard =(String) bundleKeys.nextElement();
                    password = validCreditCards.getString(numberCard);
                    if (card.equals(numberCard) && pass.equals(password))
                    {
                        ConsoleHelper.writeMessage(String.format(res.getString("success.format"), card));
                        isValidData = true;
                        break;
                    }
                }
                if (!isValidData){
                    ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), card));
                    continue;
                }
            }
            if (!isValidData){
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
        }
    }
}
