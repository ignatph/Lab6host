package validators;

import body.OrganizationType;

/**
 * Contains validator for organization
 * Validates input value != null
 */
public class OrganizationValidator implements Validator<OrganizationType> {
    public String getDescr(){return "Ошибка: Некорректный тип организации!";};
    @Override
    public boolean validate(OrganizationType type) {
        return type != null;
    }
}
