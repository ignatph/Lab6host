package client.validators;

import client.enums.OrganizationType;

import java.util.Arrays;

/**
 * Валидатор для названия организации
 * Проверяет, что введенная строка соответствует OrganizationType или "null"
 */
public class OrganizationValidator implements Validator<String> {
    public String getDescr() {
        return "Ошибка: Некорректный тип организации! Допустимые значения: "
                + Arrays.toString(OrganizationType.values()) + " или null";
    }

    @Override
    public boolean validate(String input) {
        if (input == null || input.equalsIgnoreCase("null")) {
            return true; // null разрешен
        }
        try {
            OrganizationType.valueOf(input.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}