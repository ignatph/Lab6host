package client.manager;

import client.enums.*;
import client.exception.InvalidInputException;
import client.utillity.Printer;
import client.utillity.Reader;
import client.validators.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

public class UserManager {
    private static boolean flag;
    private final Reader reader;
    private final Printer printer;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public UserManager(Reader reader) {
        this.reader = reader;
        this.printer = new Printer();
        initializeConnection();
    }

    private void initializeConnection() {
        int maxAttempts = 5;
        int retryDelay = 3000;
        int attempts = 0;
        boolean connected = false;

        while (attempts < maxAttempts && !connected) {
            try {
                attempts++;
                printer.print("Попытка подключения #" + attempts);

                // Создаем новый сокет при каждой попытке
                socket = new Socket();
                socket.connect(new InetSocketAddress("localhost", 12345), 2000);

                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());

                printer.print("Подключение установлено");
                connected = true;
                return;

            } catch (IOException e) {
                printer.print("Ошибка подключения: " + e.getMessage());
                closeConnection(); // Закрываем неудачное соединение

                if (attempts < maxAttempts) {
                    printer.print("Повторная попытка через " + (retryDelay / 1000) + " сек...");
                    try {
                        Thread.sleep(retryDelay);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        printer.print("Подключение прервано");
                        break;
                    }
                }
            }
        }

        if (!connected) {
            printer.print("Не удалось подключиться к серверу после " + maxAttempts + " попыток");
            setIsInWork(false);
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

        try {
            ClientCommand command = new ClientCommand(commandName, argument);
            switch (commandName) {
                case "add":
                case "add_if_min":
                case "update_id":
                    WorkerDTO workerDTO = collectWorkerData();
                    command.setData(workerDTO);
                    break;

                case "remove_by_id":
                case "filter_greater_than_end_date":
                    validateAndSetArgument(command, argument);
                    break;



            }
            oos.writeObject(command);
            oos.flush();
            Object response = ois.readObject();// ответ от сервера
            printer.print(response.toString());
        } catch (IOException | ClassNotFoundException e) {
            printer.print("Ошибка выполнения команды: " + e.getMessage());

        } catch (InvalidInputException e) {
            printer.print("Ошибка ввода: " + e.getMessage());
        }
    }

    private void validateAndSetArgument(ClientCommand command, String argument) throws InvalidInputException {
        if (argument == null || argument.isEmpty()) {
            throw new InvalidInputException("Команда требует аргумент!");
        }
        command.setArgument(argument);
    }

    private boolean validateCommand(String commandName, String argument) {
        //if (!descriptionMap.containsKey(commandName)) {
        //  printer.print("Неизвестная команда: " + commandName);
        // return false;
        // }
        if ("add".equals(commandName) && argument != null) {
            printer.print("Команда add не требует аргументов");
            return false;
        }
        return true;
    }

    private void receiveResponse() throws IOException, ClassNotFoundException {
        Object response = ois.readObject();
        if (response instanceof String) {
            String message = (String) response;
            printer.print(message);

            if ("EXIT".equalsIgnoreCase(message)) {
                setIsInWork(false); // Завершение работы клиента
                closeConnection();
            }
        }
    }


    private WorkerDTO collectWorkerData() {
        WorkerDTO dto = new WorkerDTO();
        Scanner scanner = new Scanner(System.in);

        // Валидация имени работника
        dto.setName(validateField(
                scanner,
                "Введите имя работника: ",
                new NameValidator(),
                input -> input
        ));

        // Валидация координат
        Coordinates coordinatesDTO = new Coordinates();
        coordinatesDTO.setX(validateField(
                scanner,
                "Введите координату X (число > -848): ",
                new XValidator(),
                input -> Float.parseFloat(input.replace(",", "."))
        ));
        coordinatesDTO.setY(validateField(
                scanner,
                "Введите координату Y: ",
                new YValidator(),
                input -> Float.parseFloat(input))
        );
        dto.setCoordinates(coordinatesDTO);

        // Валидация зарплаты
        dto.setSalary(validateField(
                scanner,
                "Введите зарплату (> 0): ",
                new SalaryValidator(),
                input -> Integer.parseInt(input))
        );

        // Валидация статуса (используем локальный enum)
        dto.setStatus(validateEnum(scanner, Status.class, "статус"));

        // Валидация организации

        Organization orgDTO = new Organization();

        orgDTO.setName(
                validateField(
                        scanner,
                        "Введите название организации (или 'null'): ",
                        new OrganizationValidator(),
                        input -> {
                            if (input.equalsIgnoreCase("null")) {
                                return null; // Возвращаем null для поля name
                            }
                            return input; // Возвращаем строку как есть (если требуется OrganizationType, измените конвертер)
                        }
                )
        );
        orgDTO.setType(validateEnum(scanner, OrganizationType.class, "тип организации"));

        // Валидация адреса
        Address addressDTO = new Address();
        addressDTO.setStreet(validateField(
                scanner,
                "Введите улицу: ",
                new StreetValidator(),
                input -> input)
        );
        addressDTO.setZipCode(validateField(
                scanner,
                "Введите почтовый индекс (минимум 6 символов или 'null'): ",
                new ZipCodeValidator(),
                input -> input.equalsIgnoreCase("null") ? null : input)
        );
        orgDTO.setAddress(addressDTO);
        dto.setOrganization(orgDTO);

        // Валидация даты окончания
        dto.setEndDate(validateField(
                scanner,
                "Введите дату окончания (ГГГГ-ММ-ДД или 'null'): ",
                new DateValidator(),
                input -> input.equalsIgnoreCase("null") ? (LocalDate) null : LocalDate.parse(input)));

        // Валидация должности (используем локальный enum)
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