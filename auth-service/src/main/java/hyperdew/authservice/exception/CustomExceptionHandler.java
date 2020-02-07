package hyperdew.authservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> genericExceptionHandler(Exception ex, WebRequest wb) {
        GenericExceptionContainer exc = new GenericExceptionContainer(new Date(), ex.getMessage(), wb.getDescription(false));
        return new ResponseEntity(exc, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFound.class)
    public final ResponseEntity<Object> userNotFoundExceptionHandler(UserNotFound ex, WebRequest wb) {
        GenericExceptionContainer exc = new GenericExceptionContainer(new Date(), ex.getMessage(), wb.getDescription(false));
        return new ResponseEntity(exc, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationFailed.class)
    public final ResponseEntity<Object> authenticationFailedHandler(AuthenticationFailed ex, WebRequest wb) {
        GenericExceptionContainer exc = new GenericExceptionContainer(new Date(), ex.getMessage(), wb.getDescription(false));
        return new ResponseEntity(exc, HttpStatus.UNAUTHORIZED);
    }
}
