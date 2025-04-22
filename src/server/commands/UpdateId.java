package server.commands;

import server.body.Worker;
import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.network.WorkerDTO;
import server.utillity.Printer;

import java.util.Optional;

public class UpdateId extends Command {
    private CommandStatusResponse response;

    public UpdateId(String description, boolean hasArgs, CollectionWorker workerCollection) {
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

            long id = Long.parseLong(getArgs().toString());
            WorkerDTO dto = (WorkerDTO) data;
            Worker updatedWorker = ConvertDTOtoWorker.toWorker(dto);
            updatedWorker.setId(id);

            Optional<Worker> workerOptional = collection.getCollection().stream()
                    .filter(e -> e.getId() == id)
                    .findFirst();

            if (workerOptional.isPresent()) {
                collection.getCollection().removeIf(w -> w.getId() == id);
                collection.getCollection().add(updatedWorker);
                response = CommandStatusResponse.ofString("Элемент с id " + id + " успешно обновлён!", true);
            } else {
                response = CommandStatusResponse.ofString("Элемента с id " + id + " не найдено!", false);
            }

        } catch (NumberFormatException e) {
            response = CommandStatusResponse.ofString("Ошибка: неверный формат ID!", false);
        } catch (ClassCastException e) {
            response = CommandStatusResponse.ofString("Ошибка формата данных для обновления!", false);
        } catch (Exception e) {
            response = CommandStatusResponse.ofString("Ошибка обновления: " + e.getMessage(), false);
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
                    "Ошибка: требуется аргумент ID элемента!",
                    false
            );
            return false;
        }

        try {
            Long.parseLong(inputArgs.toString());
            return true;
        } catch (NumberFormatException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: аргумент должен быть числом!",
                    false
            );
            return false;
        }
    }
}