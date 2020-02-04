package hyperdew.apigw.authorization;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@RibbonClient(name = "auth-service")
@FeignClient(name = "auth-service")
public interface AuthServiceClientKit {

    @GetMapping("/auth")
    public AuthResponse getAuthToken();
}
