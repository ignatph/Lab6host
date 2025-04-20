package commands;

import collection.CollectionWorker;
import commandManager.CommandsManager;
import manager.UserManager;
import utillity.Printer;

import java.util.Map;

/**
 * Class contains implementation of help command
 * Output all the commands
 */

public class Help extends Command {
    public Help(String description, boolean hasArgs,  CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            int count = 1;
            for (Map.Entry<String, Command> command : new CommandsManager(collection).getOpis().entrySet()) {
                printer.print(count++ + ". " + command.getKey() + " " + command.getValue().getDescription());
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды help нет аргументов! Введите команду без аргументов!");
            return false;
        }
    }


}
