package server.interfaces;

import utillity.Printer;

/**
 * Contains method for executing commands
 */
public interface Executable {
    void execute(Printer printer, Object data); // Добавляем параметр data
}