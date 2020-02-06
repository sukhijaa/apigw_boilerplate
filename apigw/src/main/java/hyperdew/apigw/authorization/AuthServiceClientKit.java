package hyperdew.apigw.authorization;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@RibbonClient(name = "auth-service")
@FeignClient(name = "auth-service")
public interface AuthServiceClientKit {

    @GetMapping("/authenticate")
    AuthResponse authenticateUserBasedOnAccessToken(
            @RequestHeader("app-secret") String appSecret,
            @RequestHeader("access-token") String accessToken);

    @GetMapping("/authenticate/generateToken")
    AuthResponse getAccessTokenBasedOnAppSecret(
            @RequestHeader("app-secret") String appSecret);

    @PostMapping("/authenticate/createUser")
    AuthResponse createNewUserForAppSecret(
            @RequestHeader("app-secret") String appSecret,
            @RequestBody CreateUserBody userDetails);

    @PostMapping("/authenticate/login")
    AuthResponse logUserInUsingCredentials(
            @RequestParam("username") String userName,
            @RequestParam("password") String passWord
    );
}
