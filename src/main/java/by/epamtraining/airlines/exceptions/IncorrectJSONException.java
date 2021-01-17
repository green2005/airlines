package by.epamtraining.airlines.exceptions;

public class IncorrectJSONException extends RuntimeException {
    public IncorrectJSONException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectJSONException(String message) {
        super(message);
    }
}
