package hospital.enums;

/**
 * Enum class to represent the type of function of a person
 */
public enum TypeOfFunction {
    /**
     * Enum constant representing the type of function of a person
     */
    DOCTOR("Doctor"),
    /**
     * Enum constant representing the type of function of a person
     */
    NURSE("Nurse"),
    /**
     * Enum constant representing the type of function of a person
     */
    ADMINISTRATOR("Administrator"),
    /**
     * Enum constant representing the type of function of a person
     */
    CLEANER("Cleaner"),
    /**
     * Enum constant representing the type of function of a person
     */
    SECURITY("Security"),
    /**
     * Enum constant representing the type of function of a person
     */
    VISITOR("Visitor"),
    /**
     * Enum constant representing the type of function of a person
     */
    PATIENT("Patient");

    /**
     * The display name of the enum constant
     */
    private final String displayName;

    /**
     * Constructor to initialize the display name for each enum constant
     *
     * @param displayName
     */
    TypeOfFunction(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
