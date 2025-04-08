package commands;

import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;


/**
 * Class contains implementation of info command
 * Outputs basic information about elements in collection
 */


public class Info extends Command {
    public Info(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            printer.print("Тип коллекции:" + collection.getCollection().getClass().getSimpleName());
            printer.print("Дата инициализации" + collection.getCreationDate());
            printer.print("Количество элементов:" + collection.getCollection().size());
        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды info нет аргументов! Введите команду без аргументов!");
            return false;
        }
    }
}
