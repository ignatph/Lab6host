package client.main.server.src.validator;
/**
 * Contains validator for street
 * Validates input value != null and value not empty
 */
public class StreetValidator implements Validator {
    public static boolean validate(String street) {
        return street != null && !street.trim().isEmpty();
    }
}