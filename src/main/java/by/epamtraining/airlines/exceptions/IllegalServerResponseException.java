package by.epamtraining.airlines.exceptions;

public class IllegalServerResponseException extends Exception {
    public IllegalServerResponseException(int responseCode) {
        super(String.format("Server returned %d response", responseCode));
    }
}
