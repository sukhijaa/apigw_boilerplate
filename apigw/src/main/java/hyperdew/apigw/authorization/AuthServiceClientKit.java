package hyperdew.apigw.authorization;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RibbonClient(name = "auth-service")
@FeignClient(name = "auth-service")
public interface AuthServiceClientKit {

    @GetMapping("/")
    AuthResponse authenticateUserBasedOnAccessToken(
            @RequestHeader("app-secret") String appSecret,
            @RequestHeader("access-token") String accessToken);

    @GetMapping("/authenticate/generatetoken")
    AuthResponse getAccessTokenBasedOnAppSecret(@RequestHeader("app-secret") String appSecret);

    @PostMapping("/authenticate/login")
    AuthResponse logUserInUsingCredentials(
            @RequestParam("username") String userName,
            @RequestParam("password") String passWord);
}
