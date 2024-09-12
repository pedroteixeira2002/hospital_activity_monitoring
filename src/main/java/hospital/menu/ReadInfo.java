package hospital.menu;

import hospital.enums.TypeOfFunction;

import java.io.IOException;

/**
 * This class contains methods that are used to read from the keyboard.
 */
public class ReadInfo {

    /**
     * This method returns a name that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String readName() throws IOException {
        System.out.println("\nName :\t");
        return Tools.getString();
    }

    /**
     * This method returns the age that is read from the keyboard.
     *
     * @return The integer that was read from the keyboard.
     * @throws IOException If the integer is not in conformity.
     */
    public static int readAge() throws IOException {
        System.out.println("\nAge :\t");
        return Tools.getInt();
    }

    /**
     * This method returns the type of function .
     *
     * @return The integer that was read from the keyboard.
     * @throws IOException If the integer is not in conformity.
     */
    public static TypeOfFunction manageAccess() throws IOException {
        System.out.println("Please choose a function type from the list below: ");
        TypeOfFunction[] functionTypes = TypeOfFunction.values();
        for (int i = 0; i < functionTypes.length; i++) {
            System.out.println((i + 1) + ". " + functionTypes[i]);
        }
        int choice = 0;
        boolean validChoice = false;

        // Loop until a valid choice is made
        while (!validChoice) {
            System.out.print("Enter the number corresponding to your choice: ");
            choice = Tools.getInt(); // Use your existing getInt() function

            if (choice > 0 && choice <= functionTypes.length) {
                validChoice = true; // Break out of the loop if a valid choice is made
            } else {
                System.out.println("Invalid choice. Please select a number between 1 and " + functionTypes.length + ".");
            }
        }
        return functionTypes[choice - 1]; // Return the selected TypeOfFunction
    }
}
