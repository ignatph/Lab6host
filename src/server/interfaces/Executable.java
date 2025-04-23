package server.interfaces;

import server.network.CommandStatusResponse;
import server.utillity.Printer;

/**
 * Contains method for executing commands
 */
public interface Executable {
    void execute(Printer printer, Object data);
    CommandStatusResponse getResponse();
}