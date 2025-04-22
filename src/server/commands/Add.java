package server.commands;

import server.collection.CollectionWorker;

import server.network.CommandStatusResponse;

import server.network.WorkerDTO;
import server.utillity.Printer;
import server.body.Worker;

public class Add extends Command {
    private CommandStatusResponse response;

    public Add(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            WorkerDTO dto = (WorkerDTO) data;
            Worker worker = ConvertDTOtoWorker.toWorker(dto);

            boolean idExists = collection.getCollection()
                    .stream()
                    .anyMatch(w -> w.getId() == worker.getId());

            if (idExists) {
                response = CommandStatusResponse.ofString("Error: Worker with this ID already exists", false);
            } else {
                collection.getCollection().add(worker);
                response = CommandStatusResponse.ofString("Worker successfully added to collection", true);
            }
        } catch (ClassCastException e) {
            response = CommandStatusResponse.ofString("Invalid data format for add command", false);
        }
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs != null) {
            printer.print("Add command doesn't require arguments!");
            return false;
        }
        return true;
    }
}