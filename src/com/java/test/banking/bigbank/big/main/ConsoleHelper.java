package main;

import exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper
{
    public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en", Locale.ENGLISH);

    public ConsoleHelper(){

    }

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        while (true) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            String[] amounts = readString().split("\\s");
            Integer denomination = Integer.parseInt(amounts[0]);
            Integer count = Integer.parseInt(amounts[1]);
            if (denomination > 0 && count > 0) {
                return amounts;
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static String askCurrencyCode() throws InterruptOperationException
    {
        while (true) {
            writeMessage(res.getString("choose.currency.code"));
            String curCode = readString();
            if (curCode.length() == 3) {
                return curCode.toUpperCase();
            } else {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static String readString() throws InterruptOperationException
    {
        String line = "";
        try{
            line = reader.readLine();
        }
        catch (IOException e){ }
        if (line.equalsIgnoreCase(res.getString("operation.EXIT")))
        {
            throw new InterruptOperationException();
        }else return line;
    }

    public static void printExitMessage(){
        writeMessage(res.getString("the.end"));
    }


    public static Operation askOperation() throws InterruptOperationException
    {
        while (true) {
            writeMessage(res.getString("choose.operation") +": 1 - " + res.getString("operation.INFO") +
                    ", 2 - " + res.getString("operation.DEPOSIT") + ", 3 - " + res.getString("operation.WITHDRAW")
                    + ", 4 - "+ res.getString("operation.EXIT"));
            String s = readString();
            try {
                return Operation.getAllowableOperationByOrdinal(Integer.parseInt(s));
            }catch (Exception e){
                writeMessage(res.getString("invalid.data"));
            }
        }
    }
}
