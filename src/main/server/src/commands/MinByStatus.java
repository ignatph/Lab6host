package commands;

import body.Worker;
import collection.CollectionWorker;
import manager.UserManager;
import utillity.Printer;

import java.util.Comparator;
import java.util.Collections;
/**
 * Class contains implementation of Max_by_position command
 * Output any object from the collection with the maximum value of the position field.
 */

public class MinByStatus extends Command {
    public MinByStatus(String description, boolean hasArgs,CollectionWorker workerCollection) {
        super(description, hasArgs, workerCollection);
    }

    // Компаратор для сравнения статусов
    public static class StatusComparator implements Comparator<Worker> {
        @Override
        public int compare(Worker w1, Worker w2) {
            return w1.getStatus().compareTo(w2.getStatus());
        }
    }

    @Override
    public void execute(Printer printer) {
        if (collection.getCollection().isEmpty()) {
            printer.print("Коллекция пуста!");
            return;
        }

        // Находим объект с минимальным статусом
        Worker minStatusWorker = Collections.min(
                collection.getCollection(),
                new StatusComparator()
        );

        // Выводим результат
        printer.print("Объект с минимальным статусом:");
        printer.print(minStatusWorker.toString());
    }

    @Override
    public boolean checkArgument(Printer printer, Object inputArgs) {
        if (inputArgs != null) {
            printer.print("Эта команда не принимает аргументов!");
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "min_by_status : вывести любой объект из коллекции, значение поля status которого является минимальным";
    }
}