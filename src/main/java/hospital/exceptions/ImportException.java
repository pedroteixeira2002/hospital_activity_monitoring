package hospital.exceptions;

/**
 * Exception class to represent an exception that occurs when importing a file
 */
public class ImportException extends Exception {
    /**
     * Constructor to initialize the exception with a message
     *
     * @param message The message to be displayed when the exception is thrown
     * @param cause   The cause of the exception
     *                <p>
     *                The message to be displayed when the exception is thrown
     */
    public ImportException(String message, Throwable cause) {
        super(message, cause);
    }
}
