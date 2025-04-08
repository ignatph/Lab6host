package validator;

/**
 * Contains validator for X coordinate
 * Validates input value != null and value > - 848
 */
public class XValidator implements Validator {
    public static boolean validate(Float x) {
        return x != null && x > -848;
    }
}