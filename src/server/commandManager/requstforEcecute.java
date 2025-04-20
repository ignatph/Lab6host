package server.commandManager;

import java.util.List;

/**
 * Method for working with script from user file
 *
 * @param list
 */
public class requstforEcecute(){
    public void requestCommandForScript(List<String> list) {
        try {
            for (String command : list) {
                command = command.replaceAll("\\s+", " ").trim().strip();
                System.out.println("\nСейчас выполняется команда " + command);
                checkAndStartCommand(command);
            }
         } catch (StackOverflowError ex) {
             System.err.println("\nСкрипт вызывает сам себя! Выход из скрипта");
            }

    }}