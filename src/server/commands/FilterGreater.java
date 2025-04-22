package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class FilterGreater extends Command {
    private CommandStatusResponse response;

    public FilterGreater(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString(
                        "Ошибка: для команды filter_greater_than_end_date требуется аргумент endDate!",
                        false
                );
                return;
            }

            LocalDate targetDate = LocalDate.parse(getArgs().toString());

            List<String> result = collection.getCollection().stream()
                    .filter(worker -> worker.getEndDate() != null)
                    .filter(worker -> worker.getEndDate().isAfter(targetDate))
                    .map(Worker::toString)
                    .collect(Collectors.toList());

            if (result.isEmpty()) {
                response = CommandStatusResponse.ofString(
                        "Нет элементов с endDate > " + targetDate,
                        true
                );
            } else {
                String output = "Элементы с endDate > " + targetDate + ":\n" +
                        String.join("\n", result);

                response = CommandStatusResponse.ofString(output, true);
            }

        } catch (DateTimeParseException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка формата даты! Используйте формат ГГГГ-ММ-ДД",
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
        return inputArgs != null;
    }
}