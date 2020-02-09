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
import hyperdew.authservice.utils.RequestContextUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/")
    public AuthResponse authenticateUserBasedOnAccessToken(
            @RequestHeader("app-secret") String appSecret,
            @RequestHeader("access-token") String accessToken) {
        AuthResponse newResponse = new AuthResponse();
        newResponse.setRefreshToken(accessToken);
        newResponse.setAccessToken(accessToken);
        newResponse.setAppSecret(appSecret);
        newResponse.setTokenExpiry(jwtTokenUtil.getExpirationDateFromToken(accessToken));
        newResponse.setUserName(jwtTokenUtil.getUsernameFromToken(accessToken));

        return newResponse;
    }

    @GetMapping("/generatetoken")
    public AuthResponse getAccessTokenBasedOnAppSecret(@RequestHeader("app-secret") String appSecret) {
        appSecret = RequestContextUtils.getAccessTokenFromRequestHeader(appSecret);

        String appId = StringUtils.defaultString(jwtTokenUtil.getIdFromToken(appSecret));
        ApplicationModel appData = Optional.ofNullable(applicationRepository.findById(Long.parseLong(appId)))
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

        String jwtToken = jwtTokenUtil.generateToken(userDetails);

        return new AuthResponse(jwtToken);
    }

}
