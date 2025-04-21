package client.validators;


import client.enums.Status;

/**
 * Contains validator for status
 * Validates input value != null
 */
public class StatusValidator implements Validator<Status> {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    @Override
    public boolean validate(Status status) {
        return status != null;
    }
}