package server.commands;

import server.collection.CollectionWorker;
import server.utillity.WorkerDataCollector;
import utillity.IDGenerator;
import utillity.Printer;
import server.body.Worker;
import client.manager.WorkerDTO;

public class Add extends Command {
    public Add(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        if (checkArgument(printer, getArgs())) {
            try {
                WorkerDTO dto = (WorkerDTO) data;
                Worker worker = new Worker();

                // Автоматическая генерация полей
                worker.setId(IDGenerator.generateUniqueId());
                worker.setCreationDate(LocalDate.now());

                // Заполнение данных из DTO
                WorkerDataCollector.fillWorkerFromDTO(worker, dto);

                // Проверка уникальности через Stream API
                boolean exists = collection.getWorkers().stream()
                        .anyMatch(w -> w.getId() == worker.getId());

                if (!exists) {
                    collection.addWorker(worker);
                    printer.print("Объект успешно добавлен!");
                } else {
                    printer.print("Ошибка: объект с таким ID уже существует!");
                }

            } catch (ClassCastException e) {
                printer.print("Ошибка формата данных: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs != null) {
            printer.print("У команды add не должно быть аргументов!");
            return false;
        }
        return true;
    }
}