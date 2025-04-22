package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Reorder extends Command {
    private CommandStatusResponse response;

    public Reorder(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: команда не принимает аргументы!",
                        false
                );
                return;
            }

            List<Worker> reversed = collection.getCollection().stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                Collections.reverse(list);
                                return list;
                            }
                    ));

            collection.getCollection().clear();
            reversed.forEach(collection.getCollection()::push);

            response = CommandStatusResponse.ofString(
                    "Коллекция успешно отсортирована в обратном порядке. Элементов: " + reversed.size(),
                    true
            );

        } catch (UnsupportedOperationException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: операция изменения коллекции не поддерживается",
                    false
            );
        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка при переупорядочивании: " + e.getMessage(),
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
            response = CommandStatusResponse.ofString(
                    "Ошибка: команда reorder не требует аргументов!",
                    false
            );
            return false;
        }
        return true;
    }
}