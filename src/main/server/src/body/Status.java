package body;
/**
 * Model of Status, contains getters/setter for fields of enum
 */
public enum Status {
    FIRED,
    HIRED,
    RECOMMENDED_FOR_PROMOTION,
    REGULAR;

    @Override
    public String toString() {
        return this.name();
    }
}