package validator;

/**
 * Contains validator for zipcode
 * Validates input value != null or length of value >= 6
 */
public class ZipCodeValidator implements Validator {
    public static boolean validate(String zipCode) {
        return zipCode == null || zipCode.length() >= 6;
    }
}