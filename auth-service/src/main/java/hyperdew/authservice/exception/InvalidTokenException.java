package hyperdew.authservice.exception;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String s) {
        super(s);
    }
}
