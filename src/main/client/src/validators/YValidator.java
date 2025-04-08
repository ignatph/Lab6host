package validators;

/**
 * Contains validator for Y coordinate
 * Validates input value != null
 */
public class YValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(Float y) {
        return y != null;
    }
}