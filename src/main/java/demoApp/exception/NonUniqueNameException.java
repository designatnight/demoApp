package demoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class NonUniqueNameException extends RuntimeException {
    public NonUniqueNameException(String message) {
        super(message);
    }
}
