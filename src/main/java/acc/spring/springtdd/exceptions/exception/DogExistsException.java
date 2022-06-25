package acc.spring.springtdd.exceptions.exception;

public class DogExistsException extends RuntimeException{
    public DogExistsException (String message) {
        super(message);
    }

    public DogExistsException (String message, Throwable cause) {
        super(message, cause);
    }
}