package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.Optional;

public class RemoveId extends Command {
    private CommandStatusResponse response;

    public RemoveId(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (collection.getCollection().isEmpty()) {
                response = CommandStatusResponse.ofString("Коллекция пуста!", false);
                return;
            }

            if (!checkArgument(printer, getArgs())) {
                return;
            }

            int id = Integer.parseInt(getArgs().toString());

            Optional<Worker> workerToRemove = collection.getCollection().stream()
                    .filter(worker -> worker.getId() == id)
                    .findFirst();

            if (workerToRemove.isPresent()) {
                collection.getCollection().removeIf(worker -> worker.getId() == id);
                response = CommandStatusResponse.ofString(
                        "Элемент с id " + id + " успешно удален из коллекции!",
                        true
                );
            } else {
                response = CommandStatusResponse.ofString(
                        "Элемент с id " + id + " не найден!",
                        false
                );
            }

        } catch (NumberFormatException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: аргумент должен быть целым числом!",
                    false
            );
        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка выполнения команды: " + e.getMessage(),
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
        if (inputArgs == null) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: требуется аргумент id!",
                    false
            );
            return false;
        }

        try {
            Integer.parseInt(inputArgs.toString());
            return true;
        } catch (NumberFormatException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: аргумент должен быть целым числом!",
                    false
            );
            return false;
        }
    }
}