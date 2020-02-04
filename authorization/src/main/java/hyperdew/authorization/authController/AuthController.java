package hyperdew.authorization.authController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AuthController {

    @GetMapping("/")
    public String insideAuthService() {
        return "Inside Auth MS";
    }

    @GetMapping("/auth")
    public AuthResponse getAuthToken() {
        AuthResponse response = new AuthResponse();
        response.setAccessToken("access-token-test");
        response.setAppSecret("app-secret-dummy");
        response.setRefreshToken("refresh-token-dummy");
        response.setTokenExpiry(new Date());
        response.setUserName("dummyUser");
        return response;
    }

    @GetMapping("/getAuthToken")
    public AuthResponse getAuthTokenMethod() {
        AuthResponse response = new AuthResponse();
        response.setAccessToken("access-token-test");
        response.setAppSecret("app-secret-dummy");
        response.setRefreshToken("refresh-token-dummy");
        response.setTokenExpiry(new Date());
        response.setUserName("dummyUser");
        return response;
    }
}
