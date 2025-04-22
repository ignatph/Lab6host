package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.NoSuchElementException;

public class RemoveLast extends Command {
    private CommandStatusResponse response;

    public RemoveLast(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString("Команда не принимает аргументы!", false);
                return;
            }

            // Используем Stream API для безопасного удаления последнего элемента
            Worker removed = collection.getCollection().stream()
                    .reduce((first, second) -> second) // Получаем последний элемент
                    .map(worker -> {
                        collection.getCollection().remove(worker);
                        return worker;
                    })
                    .orElseThrow(() -> new NoSuchElementException("Коллекция пуста"));

            response = CommandStatusResponse.ofString(
                    "Последний элемент с ID " + removed.getId() + " успешно удален!",
                    true
            );

        } catch (NoSuchElementException e) {
            response = CommandStatusResponse.ofString(e.getMessage(), false);
        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка удаления: " + e.getMessage(),
                    false
            );
        }
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs != null) {
            response = CommandStatusResponse.ofString("Ошибка: команда remove_last не требует аргументов!", false);
            return false;
        }
        return true;
    }
}