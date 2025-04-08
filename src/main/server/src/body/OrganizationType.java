package body;
/**
 * Model of OrganizationType, contains getters/setter for fields of enum
 */
public enum OrganizationType {
    COMMERCIAL,
    PUBLIC,
    GOVERNMENT,
    TRUST,
    PRIVATE_LIMITED_COMPANY;

    @Override
    public String toString() {
        return this.name();
    }
}