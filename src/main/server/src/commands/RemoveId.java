package commands;


import body.Worker;
import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;


/**
 * Class contains implementation of remove_at command
 * Deletes element from collection by id
 */

public class RemoveId extends Command {
    public RemoveId(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);


    }

    @Override
    public void execute(Printer printer) {
        if (collection.getCollection().isEmpty()) {
            printer.print("Коллекция пуста!");
        } else {
            if (checkArgument(new Printer(), getArgs())) {
                int id = Integer.parseInt(getArgs().toString());
                boolean found = false;
                for (Worker worker : collection.getCollection()) {
                    if (worker.getId() == id) {
                        collection.getCollection().remove(worker);
                        printer.print("Элемент с id ––" + worker.getId() + " успешно удален из коллекции!");
                        found = true;
                        break; // чтобы линкед лист не тупил и не считал вечность большую коллекцию
                    }
                }
                if (!found) {
                    printer.print("Элемент с id –– " + id + " не найден! Попробуйте еще раз");
                }
            }
        }
    }


    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды remove_by_id должен быть аргумент – id элемента коллекции!");
            return false;
        } else {
            try {
                Integer.parseInt(getArgs().toString());
                return true;
            } catch (NumberFormatException ex) {
                printer.print("Команда remove_by_id принимает на вход в качестве аргумента только целые, положительные id !");
                return false;
            }
        }
    }
}
