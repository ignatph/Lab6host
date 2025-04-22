package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.Comparator;
import java.util.Optional;

public class MinByStatus extends Command {
    private CommandStatusResponse response;

    public MinByStatus(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, data)) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: команда не принимает аргументы!",
                        false
                );
                return;
            }

            // Используем Stream API для поиска минимального элемента
            Optional<Worker> minWorker = collection.getCollection().stream()
                    .min(Comparator.comparing(Worker::getStatus));

            minWorker.ifPresentOrElse(
                    worker -> response = CommandStatusResponse.ofString(
                            "Объект с минимальным статусом:\n" + worker,
                            true
                    ),
                    () -> response = CommandStatusResponse.ofString(
                            "Коллекция пуста!",
                            false
                    )
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
        return inputArgs == null;
    }

    @Override
    public String toString() {
        return "min_by_status : вывести любой объект из коллекции, значение поля status которого является минимальным";
    }
}