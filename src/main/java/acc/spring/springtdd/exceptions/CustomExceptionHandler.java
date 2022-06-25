package acc.spring.springtdd.exceptions;

import acc.spring.springtdd.exceptions.exception.DogCustomException;
import acc.spring.springtdd.exceptions.exception.DogExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(value = {DogCustomException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND) // <- add to swagger
    public ResponseEntity<ExceptionResponse> handleNotFoundDogException(DogCustomException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.getMessage(),
                e,
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {DogExistsException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ExceptionResponse> handleDogAlreadyExistsException(DogCustomException e){
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(exceptionResponse,HttpStatus.FORBIDDEN);
    }
}
