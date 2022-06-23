package acc.spring.springtdd.exceptions;

public class DogCustomException extends RuntimeException{
    public DogCustomException(String message) {
        super(message);
    }

    public DogCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
