package demoApp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DemoObjectNotFoundException extends RuntimeException {

    public DemoObjectNotFoundException(String message) {
        super(message);
    }
}

