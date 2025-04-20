package client.validators;

/**
 * Contains validator for X coordinate
 * Validates input value != null and value > - 848
 */
public class XValidator implements Validator<Float> {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    @Override
    public boolean validate(Float x) {
        return x != null && x > -848;
    }
}