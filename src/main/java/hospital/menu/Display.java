package hospital.menu;

/**
 * This class contains methods that are used to display the menu.
 * It is used to display the main menu and the submenus.
 */
public class Display {
    /**
     * This method returns a string that is used to display the main menu.
     *
     * @return The string that was used to display the main menu.
     */
    public static String displayMainMenu() {
        return " ____________________________________________________________________\n" +
                "|                   Hospital Management                             |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Rooms Management                                               |\n" +
                "| 2. Individuals Management                                         |\n" +
                "| 3. Registry Management                                            |\n" +
                "| 4. Everyone who had contact with...                               |\n" +
                "| 5. Get actual location...                                         |\n" +
                "| 6. Quickest exit for...                                           |\n" +
                "| 7. Hospital Map                                                   |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|";
    }

    /**
     * This method returns a string that is used to display the new room menu.
     *
     * @return The string that was used to display the new room menu.
     */
    public static String roomMenu() {
        return " ____________________________________________________________________\n" +
                "|                        Room Management                            |\n" +
                "|___________________________________________________________________|\n" +
               /* "| 1. New room                                                       |\n" +
                "| 2. Export room to file                                            |\n" +*/
                "| 1. See all rooms                                                  |\n" +
                "| 2. Manage Room Access                                             |\n" +
                "| 3. View Room Activity                                             |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";

    }

    /**
     * This method returns a string that is used to display the access granted to a room .
     *
     * @return The string that was used to display the new room menu.
     */
    public static String displayAccess() {
        return " ____________________________________________________________________\n" +
                "|                      Manage Access to room                        |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Grant access to a room                                         |\n" +
                "| 2. Revoke access to a room                                        |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }

    /**
     * This method returns a string that is used to display the new individual menu.
     *
     * @return The string that was used to display the new individual menu.
     */
    public static String displayIndividualsMenu() {
        return " ____________________________________________________________________\n" +
                "|                          Individuals                              |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New individual                                                 |\n" +
                "| 2. Export individuals to file                                     |\n" +
                "| 3. See all individuals                                            |\n" +
                "| 4. Get all activity from individual                               |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }

    /**
     * This method returns a string that is used to display the new registry menu.
     *
     * @return The string that was used to display the new registry menu.
     */
    public static String displayRegistryMenu() {
        return " ____________________________________________________________________\n" +
                "|                            New Registry                           |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. New registry                                                   |\n" +
                "| 2. Export registry to File                                        |\n" +
                "| 3. See all registry                                               |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }

    /**
     * This method returns a string that is used to display the contact menu.
     *
     * @return The string that was used to display the contact menu.
     */
    public static String displayContactMenu() {
        return " ____________________________________________________________________\n" +
                "|                   Everyone who had contact with...                |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. An individual                                                  |\n" +
                "| 2. A room                                                         |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }

    /**
     * This method returns a string that is used to display the location menu.
     *
     * @return The string that was used to display the location menu.
     */
    public static String displayLocationMenu() {
        return " ____________________________________________________________________\n" +
                "|                   Get actual location...                          |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. An individual                                                  |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }


    /**
     * This method returns a string that is used to display the map menu.
     * @return a visual representation of the menu
     */
    public static String displayMapMenu() {
        return " ____________________________________________________________________\n" +
                "|                   Hospital Map                                    |\n" +
                "|___________________________________________________________________|\n" +
                "| 1. Show map                                                       |\n" +
                "| 2. Show all rooms                                                 |\n" +
                "| 3. Show all individuals                                           |\n" +
                "| 4. Show all registry                                              |\n" +
                "| 0. Exit                                                           |\n" +
                "|___________________________________________________________________|\n";
    }
}

