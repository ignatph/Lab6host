package validator;
/**
 * Contains validator for salary
 * Validates input value != null and value > 0
 */
public class SalaryValidator implements Validator {
    public static boolean validate(Integer salary) {
        return salary != null && salary > 0;
    }
}