package validators;

/**
 * Contains validator for name
 * Validates input value != null and if value not empty
 */
public class NameValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
