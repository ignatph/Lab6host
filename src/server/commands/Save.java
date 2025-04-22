package server.commands;

import server.collection.CollectionWorker;
import server.network.CommandStatusResponse;
import server.parse.XmlWriter;
import server.utillity.Printer;

import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Save extends Command {
    private CommandStatusResponse response;

    public Save(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    @Override
    public void execute(Printer printer, Object data) {
        try {
            if (!checkArgument(printer, getArgs())) {
                return;
            }

            XmlWriter xmlWriter = new XmlWriter(printer, collection);
            String filePath = "server/save.xml";

            Path path = Paths.get(filePath);
            if (!Files.isWritable(path)) {
                throw new AccessDeniedException("Нет прав для записи в файл");
            }

            xmlWriter.write(filePath);
            response = CommandStatusResponse.ofString(
                    "Коллекция успешно сохранена в файл: " + filePath,
                    true
            );

        } catch (AccessDeniedException e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка сохранения: Нет прав доступа к файлу",
                    false
            );
        } catch (Exception e) {
            response = CommandStatusResponse.ofString(
                    "Ошибка сохранения: " + e.getMessage(),
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
        if (inputArgs != null) {
            response = CommandStatusResponse.ofString(
                    "Ошибка: команда save не принимает аргументы!",
                    false
            );
            return false;
        }
        return true;
    }
}