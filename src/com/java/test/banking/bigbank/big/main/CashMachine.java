package main;

import command.CommandExecutor;
import exception.InterruptOperationException;

import java.util.Locale;

public class CashMachine
{
    public static final String RESOURCE_PATH = "resources.";

    public static void main(String[] args)
    {
        Locale.setDefault(Locale.ENGLISH);
        try{
            Operation operation;
            CommandExecutor.execute(Operation.LOGIN);
            do{
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);
        }catch (InterruptOperationException e){
            ConsoleHelper.printExitMessage();
        }catch (Exception e){
        }
    }
}
