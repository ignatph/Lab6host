package server.main;

import server.collection.CollectionWorker;
import server.commandManager.CommandsManager;
import server.commands.Command;
import server.commands.ClientCommand;
import server.network.CommandStatusResponse;
import server.utillity.Printer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerMain {
    private static final CollectionWorker collectionWorker = new CollectionWorker();
    private static HashMap<String, Command> descriptionMap = new CommandsManager(collectionWorker).getOpis();
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Ошибка сервера: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

            while (true) {
                ClientCommand clientCommand = (ClientCommand) ois.readObject();
                CommandStatusResponse response = executeCommand(clientCommand);
                oos.writeObject(response);
                oos.flush();

                if (clientCommand.getName().equals("exit")) break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка обработки клиента: " + e.getMessage());
        }
    }

    private static CommandStatusResponse executeCommand(ClientCommand command) {
        Command cmd = descriptionMap.get(command.getName());
        if (cmd == null) {
            return CommandStatusResponse.ofString("Неизвестная команда: " + command.getName(), false);
        }

        try {
            // Устанавливаем аргументы и данные ТОЛЬКО если команда их требует
            if (cmd.isHasArgs() ) {
                cmd.setArgs(command.getArgument());
               // cmd.setData(command.getData());
            }

            // Проверка аргументов (только для команд с аргументами)
            if (cmd.isHasArgs() && !cmd.checkArgument(new Printer(), command.getArgument())) {
                return cmd.getResponse();
            }

            // Выполнение команды
            cmd.execute(new Printer(), cmd.isHasArgs() ? cmd.getData() : null);
            return cmd.getResponse();

        } catch (Exception e) {
            return CommandStatusResponse.ofString(
                    "Ошибка ыполнения команды: " + e.getMessage(),
                    false
            );
        }
    }
}