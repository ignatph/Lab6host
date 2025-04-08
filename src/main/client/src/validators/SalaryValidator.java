package validators;

/**
 * Contains validator for salary
 * Validates input value != null and value > 0
 */
public class SalaryValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(Integer salary) {
        return salary != null && salary > 0;
    }
}