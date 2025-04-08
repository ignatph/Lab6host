package commands;

import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;


/**
 * Class contains implementation of exit command
 * Terminate program
 */
public class Exit extends Command {
    public Exit(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.print("Завершение программы!");
            UserManager.setIsInWork(false);
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("Команда exit не имеет аргументов, попробуйте ввести команду без аргументов!");
            return false;
        }
    }

}
