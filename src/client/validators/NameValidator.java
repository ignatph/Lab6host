package client.validators;

/**
 * Contains validator for name
 * Validates input value != null and if value not empty
 */


public class NameValidator implements Validator<String> {
    public String getDescr(){ return "Ошибка: Имя не может быть пустым!";};
    @Override
    public  boolean validate(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
