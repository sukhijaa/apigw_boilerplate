package hyperdew.authservice.authController;

import hyperdew.authservice.authentication.AuthResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    private AuthResponse generateDummyAuthResponse(String appSecret) {
        Calendar tokenExpiry = Calendar.getInstance();
        tokenExpiry.add(Calendar.SECOND, 60);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserName("Dummy UserName");
        authResponse.setAccessToken("Access Token : Expiring on  : " + tokenExpiry.getTime().toString());
        authResponse.setAppSecret(appSecret);
        authResponse.setTokenExpiry(tokenExpiry.getTime());
        authResponse.setRefreshToken("Dummy Refresh Token");

        return authResponse;
    }

    @GetMapping("/")
    public AuthResponse authenticateUserBasedOnAccessToken(
            @RequestHeader("app-secret") String appSecret,
            @RequestHeader("access-token") String accessToken) {
        return generateDummyAuthResponse(appSecret);
    }

    @GetMapping("/generateToken")
    public AuthResponse getAccessTokenBasedOnAppSecret(@RequestHeader("app-secret") String appSecret) {
        return generateDummyAuthResponse(appSecret);
    }

    @PostMapping("/login")
    public AuthResponse logUserInUsingCredentials(
            @RequestParam("username") String userName,
            @RequestParam("password") String passWord
    ) {
        return generateDummyAuthResponse(userName);
    }
}
