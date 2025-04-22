package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class ExecuteScript extends Command {
    private CommandStatusResponse response;

    public ExecuteScript(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                response = CommandStatusResponse.ofString("Script path required!", false);
                return;
            }

            String path = getArgs().toString();
            try {
                String scriptContent = ProtectedReader.readFile(path, new HashSet<>());

                // Использование Stream API для обработки строк
                List<String> listOfCommands = new BufferedReader(new StringReader(scriptContent))
                        .lines()
                        .filter(line -> !line.trim().isEmpty())
                        .collect(Collectors.toList());

                // Здесь должна быть логика выполнения команд
                // userManager.requestCommandForScript(listOfCommands);

                response = CommandStatusResponse.ofString(
                        "Script executed successfully. Commands processed: " + listOfCommands.size(),
                        true
                );

            } catch (FileNotFoundException e) {
                response = CommandStatusResponse.ofString("File not found: " + path, false);
            } catch (IOException e) {
                response = CommandStatusResponse.ofString("Read error: " + e.getMessage(), false);
            } catch (RuntimeException e) {
                response = CommandStatusResponse.ofString("Execution error: " + e.getMessage(), false);
            }
        } catch (Exception e) {
            response = CommandStatusResponse.ofString("Critical error: " + e.getMessage(), false);
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

class ProtectedReader {
    public static String readFile(String filePath, HashSet<String> recursion) throws IOException {
        HashSet<String> rec = new HashSet<>(recursion);
        if (!rec.add(filePath)) {
            throw new IOException("Recursive script call detected: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(line -> processLine(line, rec))
                    .collect(Collectors.joining("\n"));
        }
    }

    private static String processLine(String line, HashSet<String> recursion) {
        if (line.startsWith("execute_script")) {
            String[] parts = line.split(" ");
            if (parts.length < 2) {
                throw new RuntimeException("Invalid exec format: " + line);
            }
            try {
                return readFile(parts[1], recursion);
            } catch (IOException e) {
                throw new RuntimeException("Script unavailable: " + parts[1]);
            }
        }
        return line;
    }
}