package validators;

/**
 * Contains validator for organization
 * Validates input value != null
 */
public class OrganizationValidator implements Validator {
    public String getDescr(){return "Ошибка: Некорректный тип организации!";};
    public static boolean validate(OrganizationType type) {
        return type != null;
    }
}