package server.commands;
import server.body.*;
import server.collection.CollectionWorker;
import utillity.IDGenerator;
import utillity.Printer;
/**
 * Class contains implementation of add command
 * Adds new element to collection
 */
public class Add extends Command {
    public Add(String description, boolean hasArgs, CollectionWorker workerCollection) {
        super(description, hasArgs,workerCollection);
    }
    @Override
    public void execute(Printer printer) {
        if (checkArgument(new Printer(), getArgs())) {
            Worker worker = new Worker();
            worker.setId(IDGenerator.generateUniqueId());
            userManager.userDataCollect(worker);
            collection.addWorker(worker);
            printer.print("Объект успешно добавлен в коллекцию!");
        }
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды add нет аргументов! Введите команду без аргументов!");
            return false;
        }
    }

}