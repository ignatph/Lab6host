package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.stream.Collectors;

public class Show extends Command {
    private CommandStatusResponse response;

    public Show(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: команда show не принимает аргументы!",
                        false
                );
                return;
            }

            if (collection.getCollection().isEmpty()) {
                response = CommandStatusResponse.ofString("Коллекция пуста!", true);
                return;
            }

            String collectionContent = collection.getCollection().stream()
                    .map(Object::toString)
                    .collect(Collectors.joining("\n\n"));

            response = CommandStatusResponse.ofString(
                    "Элементы коллекции:\n" + collectionContent,
                    true
            );

        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка при выводе коллекции: " + e.getMessage(),
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
}