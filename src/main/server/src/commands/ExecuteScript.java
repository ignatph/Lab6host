package commands;

import utillity.Printer;
import collection.CollectionWorker;
import manager.UserManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
/**
 * Class contains implementation of execute_script command
 * Read and execute script from file
 */


public class ExecuteScript extends Command {
    public ExecuteScript(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            String path = getArgs().toString();
            try {
                // Чтение файла с проверкой рекурсии
                String scriptContent = ProtectedReader.readFile(path, new HashSet<>());

                // Разбивка на строки и выполнение
                List<String> listOfCommands = new ArrayList<>();
                try (BufferedReader reader = new BufferedReader(new StringReader(scriptContent))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (!line.trim().isEmpty()) {
                            listOfCommands.add(line);
                        }
                    }
                }
                userManager.requestCommandForScript(listOfCommands);

            } catch (FileNotFoundException e) {
                printer.print("Файл не найден: " + path);
            } catch (IOException e) {
                printer.print("Ошибка чтения файла: " + e.getMessage());
            } catch (RuntimeException e) {
                printer.print("Ошибка выполнения: " + e.getMessage());
            }
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            printer.print("Требуется путь к файлу скрипта!");
            return false;
        }
        return true;
    }
}
class ProtectedReader{

    //static HashSet<String> hash = new HashSet<>();
    public static String readFile(String filePath,HashSet<String> recursion) throws FileNotFoundException {
        Printer printer = new Printer();
        String line;
        StringBuilder script = new StringBuilder();
        HashSet<String> rec = new HashSet<>(recursion);
        rec.add(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine())!=null){
                if (line.contains("exec")){
                    //System.out.print();
                    try {
                        String path = line.split(" ")[1];
                        if (!(rec.contains(path))){
                            script.append(readFile(path,rec));
                        }
                    } catch (ArrayIndexOutOfBoundsException ec) {
                            printer.print("Неправильный формат команды exec: " + line);
                        } catch (RuntimeException e){
                        printer.print("Скрипт "+line.split(" ")[1] +" не доступен");}

                }else{
                    if (!line.trim().isEmpty()) {
                        script.append(line).append("\n");
                    }
                    }
            }
            reader.close();
            return script.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}