package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

public class Clear extends Command {
    private CommandStatusResponse response;

    public Clear(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString("Clear command doesn't require arguments!", false);
                return;
            }

            if (collection.getCollection().isEmpty()) {
                response = CommandStatusResponse.ofString("Collection is already empty", true);
            } else {
                collection.getCollection().clear();
                response = CommandStatusResponse.ofString("Collection cleared successfully!", true);
            }
        } catch (Exception e) {
            response = CommandStatusResponse.ofString("Error clearing collection: " + e.getMessage(), false);
        }
    }

    @Override
    public CommandStatusResponse getResponse() {
        return response;
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        return inputArgs == null; // Аргументы не требуются
    }
}