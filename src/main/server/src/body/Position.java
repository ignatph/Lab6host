package body;

/**
 * Model of Position, contains getters/setter for fields of enum
 */
public enum Position {
    MANAGER,
    HEAD_OF_DIVISION,
    DEVELOPER,
    LEAD_DEVELOPER,
    MANAGER_OF_CLEANING;

    @Override
    public String toString() {
        return this.name();
    }
}