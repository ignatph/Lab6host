package manager;


import body.*;
import collection.CollectionWorker;
import commandManager.CommandsManager;
import commands.Command;
import utillity.IDGenerator;
import utillity.Printer;
import utillity.Reader;
import validators.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.Function;

public class UserManager {
    private HashMap<String, Command> descriptionMap;
    private static boolean flag;
    private final Reader reader;
    private final Printer printer;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public UserManager(Reader reader, CollectionWorker collection) {
        this.reader = reader;
        this.printer = new Printer();
        this.descriptionMap = new CommandsManager(this, collection).getOpis();
        initializeConnection();
    }

    private void initializeConnection() {
        try {
            socket = new Socket("localhost", 12345);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            printer.print("Ошибка подключения: " + e.getMessage());
        }
    }

    static {
        System.out.println("Приложение запущено!");
        flag = true;
    }

    public static boolean isRunning() {
        return flag;
    }

    public static void setIsInWork(boolean flag) {
        UserManager.flag = flag;
    }

    public void requestInputCommand() {
        try {
            System.out.print("\nВведите команду (help для справки): ");
            String line = reader.nextLine().strip().replaceAll("\\s+", " ");
            checkAndStartCommand(line);
        } catch (NoSuchElementException ex) {
            System.out.println("Завершение программы!");
            setIsInWork(false);
            closeConnection();
        }
    }

    private void checkAndStartCommand(String line) {
        String[] inputData = line.split(" ", 2);
        String commandName = inputData[0].toLowerCase();
        String argument = inputData.length > 1 ? inputData[1] : null;

        if (!validateCommand(commandName, argument)) return;

        try {
            ClientCommand command = new ClientCommand(commandName, argument);
            if ("add".equals(commandName)) {
                WorkerDTO workerDTO = collectWorkerData();
                command.setData(workerDTO);
            }
            oos.writeObject(command);
            oos.flush();
            Object response = ois.readObject();// ответ от сервера
            printer.print(response.toString());
        } catch (IOException | ClassNotFoundException e) {
            printer.print("Ошибка выполнения команды: " + e.getMessage());
        }
    }

    private boolean validateCommand(String commandName, String argument) {
        if (!descriptionMap.containsKey(commandName)) {
            printer.print("Неизвестная команда: " + commandName);
            return false;
        }
        if ("add".equals(commandName) && argument != null) {
            printer.print("Команда add не требует аргументов");
            return false;
        }
        return true;
    }

    private WorkerDTO collectWorkerData() {
        WorkerDTO dto = new WorkerDTO();
        Scanner scanner = new Scanner(System.in);

        // Валидация и сбор данных


        // Валидация имени работника
        dto.setName(validateField(
                scanner,
                "Введите имя работника: ",
                new NameValidator(),
                input -> input // Конвертер: строка как есть
        ));

// Валидация координат (вложенный объект)
        CoordinatesDTO coordinatesDTO = new CoordinatesDTO();
        coordinatesDTO.setX(validateField(
                scanner,
                "Введите координату X (число > -848): ",
                new XValidator(),
                input -> Float.parseFloat(input.replace(",", ".")) // Конвертер в Float
        ));
        coordinatesDTO.setY(validateField(
                scanner,
                "Введите координату Y: ",
                new YValidator(),
                input -> Float.parseFloat(input) // Конвертер в Float
        ));
        dto.setCoordinates(coordinatesDTO);

// Валидация зарплаты
        dto.setSalary(validateField(
                scanner,
                "Введите зарплату (> 0): ",
                new SalaryValidator(),
                input -> Integer.parseInt(input) // Конвертер в Integer
        ));

// Валидация статуса (enum)
        dto.setStatus(validateEnum(scanner, Status.class, "статус"));

// Валидация организации (вложенный объект)
        OrganizationDTO orgDTO = new OrganizationDTO();
        orgDTO.setFullname(validateField(
                scanner,
                "Введите название организации (или 'null'): ",
                new OrganizationValidator(),
                input -> input.equalsIgnoreCase("null") ? null : input // Конвертер с обработкой null
                 ));
        orgDTO.setType(validateEnum(scanner, OrganizationType.class, "тип организации"));

// Валидация адреса организации
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(validateField(
                scanner,
                "Введите улицу: ",
                new StreetValidator(),
                input -> input // Конвертер: строка как есть
        ));
        addressDTO.setZipCode(validateField(
                scanner,
                "Введите почтовый индекс (минимум 6 символов или 'null'): ",
                new ZipCodeValidator(),
                input -> input.equalsIgnoreCase("null") ? null : input // Обработка null
        ));
        orgDTO.setAddress(addressDTO);
        dto.setOrganization(orgDTO);

// Валидация даты окончания (с поддержкой null)
        dto.setEndDate(validateField(
                scanner,
                "Введите дату окончания (ГГГГ-ММ-ДД или 'null'): ",
                new DateValidator(),
                input -> input.equalsIgnoreCase("null") ? null : LocalDate.parse(input) // Парсинг даты
        ));

// Валидация должности (enum)
        dto.setPosition(validateEnum(scanner, Position.class, "должность"));
        return dto;
    }

    // Вспомогательные методы валидации
    private <T> T validateField(Scanner scanner,
                                String prompt,
                                Validator<T> validator,
                                Function<String, T> converter) {
        T value;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                value = converter.apply(input);
                if (!validator.validate(value)) {
                    System.out.println("Некорректное значение!");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Ошибка формата данных!");
            }
        } while (true);
        return value;
    }

    private <T extends Enum<T>> T validateEnum(Scanner scanner, Class<T> enumType, String fieldName) {
        T value;
        do {
            System.out.printf("Введите %s (%s): ", fieldName, Arrays.toString(enumType.getEnumConstants()));
            String input = scanner.nextLine().trim().toUpperCase();
            try {
                value = Enum.valueOf(enumType, input);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Некорректное значение!");
            }
        } while (true);
        return value;
    }

    private void closeConnection() {
        try {
            if (ois != null) ois.close();
            if (oos != null) oos.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
        }
    }

    // Остальные методы валидации...
}