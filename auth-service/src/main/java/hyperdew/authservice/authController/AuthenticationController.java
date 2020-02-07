package hyperdew.authservice.authController;

import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.appRegistry.ApplicationRepository;
import hyperdew.authservice.authentication.AuthResponse;
import hyperdew.authservice.exception.AuthenticationFailed;
import hyperdew.authservice.exception.UserNotFound;
import hyperdew.authservice.securityConfig.JWTTokenUtil;
import hyperdew.authservice.userRegistry.UserModel;
import hyperdew.authservice.userRegistry.UserRepository;
import hyperdew.authservice.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Optional;


@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/generatetoken")
    public AuthResponse getAccessTokenBasedOnAppSecret(@RequestHeader("app-secret") String appSecret) {
        String appId = jwtTokenUtil.getIdFromToken(appSecret);
        ApplicationModel appData = Optional.ofNullable(applicationRepository.findById(appId))
                .orElseThrow(() -> new AuthenticationFailed("Failed to get Application Data from app-secret"));

        UserModel userDetails = Optional.ofNullable(
                userRepository.findByApplicationModelAndUserName(appData, Constants.GUEST_USER_NAME))
                .orElseThrow(
                        () -> new UserNotFound(String.format("Guest User not found for appName : %s", appData.getApplicationName()))
                );

        String newAuthToken = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(newAuthToken);
    }

    @PostMapping("/login")
    public AuthResponse logUserInUsingCredentials(
            @RequestParam("username") String userName,
            @RequestParam("password") String passWord) {

        UserModel userDetails = Optional.ofNullable(userRepository.findByUserName(userName))
                .orElseThrow(() -> new UserNotFound("User not found"));

        if (!bCryptPasswordEncoder.matches(passWord, userDetails.getPassword())) {
            throw new AuthenticationFailed(String.format("Password does not match for the user : %s", userName));
        }

        String jwtToken = jwtTokenUtil.getToken(userDetails.getId(), userName, null, userDetails.getUserRole());

        return new AuthResponse(jwtToken);
    }

}
