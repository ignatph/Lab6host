package collection;

import body.Worker;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.Stack;

/**
 * Contains tools for operation Worker collection
 */
public class CollectionWorker {
    private static LocalDateTime creationDate;

    public CollectionWorker() {
        creationDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    private static final Stack<Worker> workers = new Stack<>();

    public static void addWorker(Worker worker) {
        workers.push(worker);
    }

    public static Stack<Worker> getCollection() {
        return workers;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

}

