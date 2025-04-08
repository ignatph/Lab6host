package validator;
/**
 * Contains validator for name
 * Validates input value != null and if value not empty
 */
public class NameValidator implements Validator {
    public static boolean validate(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
