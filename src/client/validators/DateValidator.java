package client.validators;

import java.time.LocalDate;

public class DateValidator implements Validator<LocalDate> {
    @Override
    public boolean validate(LocalDate value) {
        // Разрешаем null
        if (value == null) {
            return true;
        }
        // Дополнительная проверка (пример: дата не должна быть в будущем)
        return !value.isAfter(LocalDate.now());
    }
}