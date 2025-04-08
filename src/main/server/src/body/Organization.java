package body;

/**
 * Model of Organization, contains getters/setter for fields of clas
 */
public class Organization {
    private String fullName;
    private OrganizationType type;
    private Address officialAddress;

    /**
     * Constructs an Organization with specified parameters.
     *
     * @param fullName          The full name of the organization
     * @param type              The type of organization
     * @param officialAddress   The official address of the organization
     */
    public Organization(String fullName, OrganizationType type, Address officialAddress) {
        setName(fullName);
        setType(type);
        setAddress(officialAddress);
    }


    public Organization() {
    }


    public String getFullName() {
        return fullName;
    }


    public void setName(String fullName) {
        this.fullName = fullName;
    }


    public OrganizationType getType() {
        return type;
    }


    public void setType(OrganizationType type) {
        this.type = type;
    }


    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void setAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }


    @Override
    public String toString() {
        return "Organization: " +
                "fullName = " + fullName +
                ", type = " + type +
                ", officialAddress = " + officialAddress;
    }
}