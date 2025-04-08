package commandManager;

import collection.CollectionWorker;
import commands.*;

import manager.UserManager;

import java.util.HashMap;

/**
 * Contains methods for storing, getting command instances
 */

public class CommandsManager {
    private final UserManager userManager;
    private final CollectionWorker collection;
    private final HashMap<String, Command> descriptionMap = new HashMap<>();

    public CommandsManager(UserManager userManager, CollectionWorker collection) {
        this.userManager = userManager;
        this.collection = collection;
    }

    private void init() {
        descriptionMap.put("help", new Help("-Вывести справку по доступным командам", false, userManager, collection));
        descriptionMap.put("info", new Info("-Вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", false, userManager, collection));
        descriptionMap.put("add", new Add("-Добавить новый элемент в коллекцию", false, userManager, collection));
        descriptionMap.put("show", new Show("-Вывести в стандартный поток вывода все элементы коллекции в строковом представлении", false, userManager, collection));
        descriptionMap.put("save", new Save("-Сохранить коллекцию в файл", true, userManager, collection));
        descriptionMap.put("update_id", new UpdateId("-обновить значение элемента коллекции, id которого равен заданному", true, userManager, collection));
        descriptionMap.put("clear", new Clear("-Очистить коллекцию", false, userManager, collection));
        descriptionMap.put("exit", new Exit("-Завершить программу (без сохранения в файл)", false, userManager, collection));

        descriptionMap.put("remove_by_id", new RemoveId("-Удалить элемент из коллекции по его id", true, userManager, collection));
        descriptionMap.put("remove_lat", new RemoveLast("-Удалить последний элемент из коллекции", true, userManager, collection));
        descriptionMap.put("add_if_min", new AddIfMin("-Добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", false, userManager, collection));

        descriptionMap.put("reorder", new Reorder("-Отсортировать коллекцию в порядке, обратном нынешнему", false, userManager, collection));
        descriptionMap.put("min_by_status", new MinByStatus("-Вывести любой объект из коллекции, значение поля status которого является минимальным", false, userManager, collection));
        descriptionMap.put("max_by_position", new MaxByPosition("-Вывести любой объект из коллекции, значение поля position которого является максимальным", true, userManager, collection));
        descriptionMap.put("filter_greater_than_end_date", new FilterGreater("-Вывести элементы, значение поля endDate которых больше заданного", true, userManager, collection));

        descriptionMap.put("exec", new ExecuteScript("-Считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", true, userManager, collection));
    }

    public HashMap<String, Command> getOpis() {
        init();
        return descriptionMap;
    }


}
