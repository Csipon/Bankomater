package command;

import exception.InterruptOperationException;

import java.sql.SQLException;

interface Command
{
    void execute() throws InterruptOperationException, ClassNotFoundException, SQLException;
}
