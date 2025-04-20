package client.main.server.src.validator;

/**
 * Contains validator for organization
 * Validates input value != null
 */
public class OrganizationValidator implements Validator {
    public static boolean validate(OrganizationType type) {
        return type != null;
    }
}