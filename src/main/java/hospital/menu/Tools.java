package hospital.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This class contains methods that are used to read from the keyboard.
 */
public class Tools {

    /**
     * This method returns a string that is read from the keyboard.
     *
     * @return The string that was read from the keyboard.
     * @throws IOException If the string is null, empty or blank.
     */
    public static String getString() throws IOException {

        BufferedReader stringIn = new BufferedReader(new
                InputStreamReader(System.in));

        return stringIn.readLine();

    }

    /**
     * This method returns an integer that is read from the keyboard.
     *
     * @return The integer that was read from the keyboard.
     * @throws IOException If the input is not an integer.
     */
    public static int getInt() throws IOException {

        String aux = getString();

        return Integer.parseInt(aux);

    }

    /**
     * This method returns a LocalDateTime object that is read from the keyboard.
     *
     * @return The LocalDateTime object that was read from the keyboard.
     * @throws IOException If the input is not in the correct format.
     */
    public static LocalDateTime getLocalDateTime() throws IOException, DateTimeParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter date-time in the format 'yyyy-MM-dd HH:mm:ss':");

        String input = reader.readLine(); // Read input from the user

        // Define the expected date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Parse the input string to a LocalDateTime object
        return LocalDateTime.parse(input, formatter);
    }

}