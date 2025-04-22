package server.commandManager;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import server.body.*;

public class ServerUserManager {
    /**
     * Отправка клиенту списка допустимых значений enum
     */
    public void sendEnumValues(ObjectOutputStream oos) throws IOException {
        Map<String, List<String>> enums = new HashMap<>();

        // Для Status
        enums.put("Status",
                Arrays.stream(Status.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));

        // Для OrganizationType и других enum
        enums.put("OrganizationType",
                Arrays.stream(OrganizationType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList()));

        oos.writeObject(enums);
        oos.flush();
    }

    /**
     * Валидация enum на сервере
     */
    public Status validateStatus(String input) throws IllegalArgumentException {
        return Status.valueOf(input.toUpperCase());
    }
}