package uk.co.techblue.exception;

/**
 * The Class CustomException.
 */
public class CustomException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4708937336839889745L;

    /**
     * Instantiates a new custom exception.
     * 
     * @param message the message
     */
    public CustomException(String message) {
        super(message);
    }

    /**
     * Instantiates a new custom exception.
     * 
     * @param throwable the throwable
     */
    public CustomException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new custom exception.
     * 
     * @param message the message
     * @param throwable the throwable
     */
    public CustomException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
