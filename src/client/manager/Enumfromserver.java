package client.manager;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;
import client.enums.*;
public class Enumfromserver {
    private Map<String, List<String>> serverEnums;

    /**
     * Получение enum-значений от сервера
     */
    public void fetchEnums(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        serverEnums = (Map<String, List<String>>) ois.readObject();

    }

    /**
     * Валидация ввода через полученные значения
     */
    public boolean validateEnumInput(String enumName, String userInput) {
        List<String> allowedValues = serverEnums.get(enumName);
        return allowedValues != null && allowedValues.contains(userInput.toUpperCase());
    }
}