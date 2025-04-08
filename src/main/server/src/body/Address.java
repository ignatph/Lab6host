package body;
/**
 * Model of Address, contains getters/setter for fields of clas
 */
public class Address {
    private String street;
    private String zipCode;

    /**
     * Constructs an Address object with specified street and postal code.
     *
     * @param street    The street name. Cannot be null or empty.
     * @param zipCode   The postal code. If not null, must be at least 6 characters long.
     * @throws IllegalArgumentException If street is null/empty or zipCode is invalid.
     */
    public Address(String street, String zipCode) {
        setStreet(street);
        setZipCode(zipCode);
    }

    /**
     * Default constructor. Creates an Address object with null-initialized fields.
     */
    public Address() {
    }


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be null or empty.");
        }
        this.street = street;
    }


    public String getZipCode() {
        return zipCode;
    }


    public void setZipCode(String zipCode) {
        if (zipCode != null && zipCode.length() < 6) {
            throw new IllegalArgumentException("Postal code must be at least 6 characters long.");
        }
        this.zipCode = zipCode; // Can be null
    }


    @Override
    public String toString() {
        return "Address: " +
                "street = " + street +
                ", zipCode = " + zipCode;
    }
}