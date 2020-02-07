package hyperdew.authservice.exception;

public class AuthenticationFailed extends RuntimeException {
    public AuthenticationFailed(String s) {
        super(s);
    }
}
