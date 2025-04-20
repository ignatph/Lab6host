package client.manager;

import java.io.Serializable;

public class AddressDTO implements Serializable {
    private String street;
    private String zipCode;

    // Геттеры и сеттеры
    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
}