package server.commands;

import server.collection.CollectionWorker;
import server.interfaces.Checkable;
import server.interfaces.Executable;
import utillity.Printer;

/**
 * Common abstract class for all commands
 */
public abstract class Command implements Executable, Checkable {

    protected final CollectionWorker collection;
    private final String description;
    private final boolean hasArgs;
    private Object args;
    private Object data; // Новое поле для хранения данных

    public Command(String description, boolean hasArgs, CollectionWorker collection) {
        this.description = description;
        this.hasArgs = hasArgs;
        this.collection = collection;
    }

    // Изменяем сигнатуру метода
    @Override
    public abstract void execute(Printer printer, Object data); // Добавляем параметр data

    @Override
    public abstract boolean checkArgument(Printer printer, Object inputArgs);

    // Новый метод для проверки данных
    public boolean checkData(Printer printer, Object inputData) {
        // Базовая реализация, может быть переопределена
        return true;
    }

    public boolean isHasArgs() {
        return hasArgs;
    }

    public String getDescription() {
        return description;
    }

    public Object getArgs() {
        return args;
    }

    public Object getData() { // Новый геттер для данных
        return data;
    }

    public void setArgs(Object args) {
        this.args = args;
    }

    public void setData(Object data) { // Новый сеттер для данных
        this.data = data;
    }
}