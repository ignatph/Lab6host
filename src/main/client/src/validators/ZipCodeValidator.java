package validators;

/**
 * Contains validator for zipcode
 * Validates input value != null or length of value >= 6
 */
public class ZipCodeValidator implements Validator {
    public String getDescr(){return "Ошибка: Имя не может быть пустым!";};
    public static boolean validate(String zipCode) {
        return zipCode == null || zipCode.length() >= 6;
    }
}