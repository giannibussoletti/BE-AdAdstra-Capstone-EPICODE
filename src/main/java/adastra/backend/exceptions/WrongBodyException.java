package adastra.backend.exceptions;

public class WrongBodyException extends RuntimeException {
    public WrongBodyException(String message) {
        super(message);
    }
}
