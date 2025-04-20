package client.main.server.src.validator;

/**
 * Contains validator for Y coordinate
 * Validates input value != null
 */
public class YValidator implements Validator {
    public static boolean validate(Float y) {
        return y != null;
    }
}