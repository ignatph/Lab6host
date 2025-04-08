import manager.UserManager;
import utillity.Reader;
import collection.CollectionWorker;

import java.time.LocalDateTime;


//add John 100.5 200.0 50000 HIRED Company COMMERCIAL MainStreet 123456
public class Main {
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
