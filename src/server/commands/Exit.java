package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

public class Exit extends Command {
    private CommandStatusResponse response;

    public Exit(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString("Exit command doesn't accept arguments", false);
                return;
            }

            //UserManager.setIsInWork(false);
            response = CommandStatusResponse.ofString("Program termination requested", true);

        } catch (Exception e) {
            response = CommandStatusResponse.ofString("Error during exit: " + e.getMessage(), false);
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