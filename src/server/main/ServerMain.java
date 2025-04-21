

package server.main;
import client.manager.ClientCommand;
import server.collection.CollectionWorker;
import server.commandManager.CommandsManager;
import server.commandManager.ServerUserManager;
import server.commands.Command;
import server.commands.*;

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
             ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());) {

            while (true) {
                //new ServerUserManager().sendEnumValues(oos);
                ClientCommand clientCommand = (ClientCommand)ois.readObject();
                String response = executeCommand(clientCommand);
                oos.writeObject(response);
                oos.flush();

                if (clientCommand.getName().equals("exit")) break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Ошибка обработки клиента: " + e.getMessage());
        }
    }
    private static String executeCommand(ClientCommand command) {
        descriptionMap.get(command).execute(new utillity.Printer());
        return "";

    }


}