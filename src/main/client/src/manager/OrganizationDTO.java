package manager;

import body.OrganizationType;
import java.io.Serializable;

public class OrganizationDTO implements Serializable {
    private String fullname;
    private OrganizationType type;
    private AddressDTO address;

    // Геттеры и сеттеры
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public OrganizationType getType() { return type; }
    public void setType(OrganizationType type) { this.type = type; }
    public AddressDTO getAddress() { return address; }
    public void setAddress(AddressDTO address) { this.address = address; }
}