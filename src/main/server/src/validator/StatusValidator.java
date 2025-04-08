package validator;

/**
 * Contains validator for status
 * Validates input value != null
 */
public class StatusValidator implements Validator {
    public static boolean validate(Status status) {
        return status != null;
    }
}