

package main;
import collection.CollectionWorker;
import commandManager.CommandsManager;
import commandManager.ServerUserManager;
import commands.Command;
import commands.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import commandManager.CommandsManager.*;

public class ServerMain {
    private HashMap<String, Command> descriptionMap = new CommandsManager(this, collectionWorker).getOpis();
    private static final int PORT = 12345;
    private static final CollectionWorker collectionWorker = new CollectionWorker();


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
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream()); {

            while (true) {
                new ServerUserManager().sendEnumValues(oos);
                Command clientCommand = (Command) ois.readObject();
                String response = executeCommand(clientCommand);
                oos.writeObject(response);
                oos.flush();

                if (clientCommand.getName().equals("exit")) break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка обработки клиента: " + e.getMessage());
        }
    }
    private static String executeCommand(Command command) {

        if (!descriptionMap.containsKey(command.getName())) {
            return "Неизвестная команда: " + command.getName();
        }
        return serverCommands.get(command.getName()).execute(command.getArgs());
    }


}