package hospital.enums;

/**
 * Enum class to represent the different types of rooms in a hospital
 */
public enum TypeOfRoom {
    /**
     * Enum constant representing the type of room in a hospital
     */
    CONSULTATION("Consultation Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    SURGERY("Surgery Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    WAITING("Waiting Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    RECOVERY("Recovery Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    HOSPITALIZATION("Hospitalization Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    EMERGENCY("Emergency Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    STORAGE("Storage Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    RESTROOM("Restroom"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    KITCHEN("Kitchen"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    OFFICE("Office"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    CANTEEN("Canteen"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    CAFE("Cafe"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    COMMON("Common Area"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    BATHROOM("Bathroom"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    LABORATORY("Laboratory"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    PHARMACY("Pharmacy"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    IMAGING("Imaging Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    RECEPTION("Reception"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    LAUNDRY("Laundry Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    MEETING("Meeting Room"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    LIBRARY("Library"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    CHURCH("Church"),
    /**
     * Enum constant representing the type of room in a hospital
     */
    EXIT("Exit");
    /**
     * The display name of the enum constant
     */
    private final String displayName;

    /**
     * Constructor to initialize the display name for each enum constant
     * @param displayName
     */
    TypeOfRoom(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
