package main;

import commands.Command;
import manager.ClientCommand;
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
import java.time.LocalDateTime;
import java.util.Scanner;


//add John 100.5 200.0 50000 HIRED Company COMMERCIAL MainStreet 123456
public class ClientMain {
    private static final String SERVER_HOST = "locaaahost";
    private static final int SERVER_PORT = 12345;
    private static final Printer printer = new Printer();
    private static final Scanner scanner = new Scanner(System.in);
    public static String FILE_PATH = "";

    public static void main(String[] args) {
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
    }
}


