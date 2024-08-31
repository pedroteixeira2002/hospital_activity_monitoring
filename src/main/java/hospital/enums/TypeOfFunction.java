package hospital.enums;

/**
 * Enum class to represent the type of function of a person
 */
public enum TypeOfFunction {
    DOCTOR("Doctor"),
    NURSE("Nurse"),
    ADMINISTRATOR("Administrator"),
    CLEANER("Cleaner"),
    SECURITY("Security"),
    VISITOR("Visitor"),
    PATIENT("Patient");

    /**
     * The display name of the enum constant
      */
    private final String displayName;

    /**
     * Constructor to initialize the display name for each enum constant
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
