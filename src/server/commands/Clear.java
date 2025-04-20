package server.commands;

import server.collection.CollectionWorker;
import utillity.Printer;

/**
 * Class contains implementation of clear command
 * Clears all collection
 */
public class Clear extends Command {
    public Clear(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs,workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            if (collection.getCollection().isEmpty()) {
                printer.print("Коллекция уже пустая");
            } else {
                collection.getCollection().clear();
                printer.print("Коллекция очищена!");
            }
        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды clear нет аргументов!");
            return false;
        }
    }

}
