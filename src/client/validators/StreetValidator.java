package client.validators;


/**
 * Contains validator for street
 * Validates input value != null and value not empty
 */
public class StreetValidator implements Validator<String> {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public boolean validate(String street) {
        return street != null && !street.trim().isEmpty();
    }
}