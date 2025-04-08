package validators;

/**
 * Contains validator for street
 * Validates input value != null and value not empty
 */
public class StreetValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(String street) {
        return street != null && !street.trim().isEmpty();
    }
}