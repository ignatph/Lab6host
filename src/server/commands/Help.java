package server.commands;

import server.collection.CollectionWorker;
import server.commandManager.CommandsManager;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Help extends Command {
    private CommandStatusResponse response;

    public Help(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: команда help не принимает аргументы!",
                        false
                );
                return;
            }

            CommandsManager commandsManager = new CommandsManager(collection);
            AtomicInteger counter = new AtomicInteger(1);

            String helpText = commandsManager.getOpis().entrySet().stream()
                    .map(entry -> String.format(
                            "%d. %s %s",
                            counter.getAndIncrement(),
                            entry.getKey(),
                            entry.getValue().getDescription()))
                    .collect(Collectors.joining("\n"));

            response = CommandStatusResponse.ofString(
                    "Доступные команды:\n" + helpText,
                    true
            );

        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка при получении списка команд: " + e.getMessage(),
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