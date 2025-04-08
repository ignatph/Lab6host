package validators;

/**
 * Contains validator for status
 * Validates input value != null
 */
public class StatusValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(Status status) {
        return status != null;
    }
}