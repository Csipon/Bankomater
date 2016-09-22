package command;

import exception.InterruptOperationException;
import main.Operation;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public final class CommandExecutor
{
    private CommandExecutor(){

    }

    private static Map<Operation, Command> map;
    static
    {
        map = new HashMap<>();
        map.put(Operation.LOGIN, new LoginCommand());
        map.put(Operation.INFO, new InfoCommand());
        map.put(Operation.DEPOSIT, new DepositCommand());
        map.put(Operation.WITHDRAW, new WithdrawCommand());
        map.put(Operation.EXIT, new ExitCommand());
    }

    public static final void execute(Operation operation) throws InterruptOperationException, SQLException, ClassNotFoundException {
        map.get(operation).execute();
    }


}