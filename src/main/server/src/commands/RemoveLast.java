package commands;

import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;
/**
 * Class contains implementation of remove command
 * Delete last element in collection
 */
public class RemoveLast extends Command {
    public RemoveLast(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (collection.getCollection().isEmpty()) {
                System.out.println("Коллекция пуста");
            } else {
                collection.getCollection().pop();
                printer.print("Последний элемент удален!");
            }
            System.out.println("Последний элемент удалён");
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды remove_last нет аргументов! Введите команду без аргументов!");
            return false;
        }
    }
}