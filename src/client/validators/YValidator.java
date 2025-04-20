package client.validators;

/**
 * Contains validator for Y coordinate
 * Validates input value != null
 */
public class YValidator implements Validator<Float> {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    @Override
    public boolean validate(Float x) {
        return x != null && x > -848;
    }
}