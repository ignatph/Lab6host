package commands;

import body.Worker;
import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;
/**
 * Class contains implementation of update command
 * Update element by id
 */

public class UpdateId extends Command {

    public UpdateId(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        if (collection.getCollection().isEmpty()) {
            printer.print("Коллекция пуста!");
        } else {
            if (checkArgument(printer, getArgs())) {
                long id = Long.parseLong(getArgs().toString());
                collection.getCollection().stream()
                        .filter(e -> e.getId() == id)
                        .findFirst()
                        .ifPresentOrElse(
                                worker -> {
                                    // Действие, если элемент найден
                                    Worker updatedWorker = userManager.userDataCollect(worker);
                                    updatedWorker.setId(id); // Сохраняем старый ID
                                    printer.print("Элемент с id " + id + " успешно обновлён!");
                                },
                                () -> {
                                    // Действие, если элемент не найден
                                    printer.print("Элемента с id " + id + " не найдено!");
                                }
                        );

            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("У команды update должен быть аргумент id (id элемента, значения которого вы хотите обновить). Попробуйте еще раз!");
            return false;
        }
        try {
            Integer.parseInt(inputArgs.toString());
            return true;
        } catch (NumberFormatException ex) {
            printer.print("Команда update имеет аргумент типа (int). Попробуйте еще раз!");
            return false;
        }
    }
}