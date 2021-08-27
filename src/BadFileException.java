/**
 * Extension of Exception for errors in the format of the file being parsed by MetroMapParser
 *
 * @see MetroMapParser
 */
public class BadFileException extends Exception {
    /**
     * Exception when there is a error in the format of the file being parsed
     *
     * @param args The exception message
     */
    public BadFileException(String args) {
        super("File Formatted Incorrectly - " + args + ".");
    }
}
