package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Info extends Command {
    private CommandStatusResponse response;

    public Info(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: команда info не принимает аргументы!",
                        false
                );
                return;
            }

            String info = Stream.of(
                    "Тип коллекции: " + collection.getCollection().getClass().getSimpleName(),
                    "Дата инициализации: " + collection.getCreationDate()
                            .format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                    "Количество элементов: " + collection.getCollection().size()
            ).collect(Collectors.joining("\n"));

            response = CommandStatusResponse.ofString(info, true);

        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка получения информации: " + e.getMessage(),
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