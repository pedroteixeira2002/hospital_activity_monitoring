package hospital.enums;

/**
 * Enum class to represent the different types of rooms in a hospital
 */
public enum TypeOfRoom {
    CONSULTATION("Consultation Room"),
    SURGERY("Surgery Room"),
    WAITING("Waiting Room"),
    RECOVERY("Recovery Room"),
    HOSPITALIZATION("Hospitalization Room"),
    EMERGENCY("Emergency Room"),
    STORAGE("Storage Room"),
    RESTROOM("Restroom"),
    KITCHEN("Kitchen"),
    OFFICE("Office"),
    CANTEEN("Canteen"),
    CAFE("Cafe"),
    COMMON("Common Area"),
    BATHROOM("Bathroom"),
    LABORATORY("Laboratory"),
    PHARMACY("Pharmacy"),
    IMAGING("Imaging Room"),
    RECEPTION("Reception"),
    LAUNDRY("Laundry Room"),
    MEETING("Meeting Room"),
    LIBRARY("Library"),
    CHURCH("Church"),
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
