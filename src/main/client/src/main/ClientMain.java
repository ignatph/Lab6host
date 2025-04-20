package main;
import commands.Command;
import manager.UserManager;
import utillity.Printer;
import utillity.Reader;
import collection.CollectionWorker;
import validators.NameValidator;
import validators.Validator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;git
import java.util.Scanner;


//add John 100.5 200.0 50000 HIRED Company COMMERCIAL MainStreet 123456
public class ClientMain {
    private static final String SERVER_HOST = "localhot t";
    private static final int SERVER_PORT = 12345;
    private static final Printer printer = new Printer();
    private static final Scanner scanner = new Scanner(System.in);
    public static String FILE_PATH = "";
    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            Reader reader = new Reader();
            System.out.println("–––––––– " + LocalDateTime.now().toString().substring(0, 10) + " ––––––––");
            System.out.println("Введите путь к файлу или нажмите Enter чтобы продолжить");
            String userInp = reader.nextLine();
            if (userInp == null) {
                System.out.println("\n");
            } else {
                FILE_PATH = userInp;
            }
            CollectionWorker worker = new CollectionWorker();
            worker.getCollection().clear();
            UserManager userManager = new UserManager(reader, worker);
            UserManager.setIsInWork(true);
            while (UserManager.isRunning()) {
                userManager.requestInputCommand();
            }




            while (true) {
                System.out.print("Введите команду (help для справки): ");
                String input = scanner.nextLine().trim();
                String[] parts = input.split(" ", 2);
                String commandName = parts[0].toLowerCase();
                String argument = (parts.length > 1) ? parts[1] : null;

                // Валидация команды на клиенте
                if (!isCommandValid(commandName, argument)) {
                    printer.print("Некорректная команда или аргумент!");
                    continue;
                }

                // Отправка команды на сервер
                Command command = new ClientCommand(commandName, argument);
                oos.writeObject(command);
                oos.flush();

                // Получение ответа от сервера
                String response = (String) ois.readObject();
                printer.print(response);

                if (commandName.equals("exit")) break;
            }
        } catch (IOException | ClassNotFoundException e) {
            printer.print("Ошибка подключения: " + e.getMessage());
        }
    }


}