package commands;

import body.Worker;
import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class contains implementation of reorder command
 * Sort collection in reverse order
 */
public class Reorder extends Command {
    public Reorder(String description, boolean hasArgs, UserManager userManager, CollectionWorker workerCollection) {
        super(description, hasArgs, userManager, workerCollection);
    }

    @Override
    public void execute(Printer printer) {
        Stack<Worker> originalStack = CollectionWorker.getCollection();
        List<Worker> tempList = new ArrayList<>();
        while (!originalStack.isEmpty()) {
            tempList.add(originalStack.pop());
        }

        originalStack.clear();

        for (Worker element : tempList) {
            originalStack.push(element);
        }

        printer.print("Коллекция отсортирована в обратном порядке.");
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs == null) {
            return true;
        } else {
            printer.print("У команды reorder нет аргументов! Введите команду без аргументов!");
            return false;
        }
    }

}