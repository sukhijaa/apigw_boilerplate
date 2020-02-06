package hyperdew.apigw.authorization;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RibbonClient(name = "auth-service")
@FeignClient(name = "auth-service")
public interface AuthServiceClientKit {

    @GetMapping("/authenticate")
    AuthResponse authenticateUserBasedOnAccessToken(
            @RequestHeader("app-secret") String appSecret,
            @RequestHeader("access-token") String accessToken);

    @GetMapping("/generateToken")
    AuthResponse getAccessTokenBasedOnAppSecret(
            @RequestHeader("app-secret") String appSecret);

    @PostMapping("/createUser")
    AuthResponse createNewUserForAppSecret(
            @RequestHeader("app-secret") String appSecret,
            @RequestBody CreateUserBody userDetails);
}
