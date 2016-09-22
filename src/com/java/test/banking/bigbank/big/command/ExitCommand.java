package command;


import exception.InterruptOperationException;
import main.CashMachine;
import main.ConsoleHelper;
import main.Operation;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

class ExitCommand implements Command
{
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en", Locale.ENGLISH);


    @Override
    public void execute() throws InterruptOperationException, SQLException, ClassNotFoundException {
        ConsoleHelper.writeMessage(res.getString("exit.question.y.n"));
        String answer = ConsoleHelper.readString();
        if (answer.trim().equalsIgnoreCase(res.getString("yes")))
        {
            ConsoleHelper.writeMessage(res.getString("thank.message"));
        } else {
            Operation operation;
            do{
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);
        }
    }
}
