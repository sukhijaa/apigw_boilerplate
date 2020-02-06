package hyperdew.authservice.userRegistry;

import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.appRegistry.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public UserModel createUser(
            @RequestHeader("app-secret") String appSecret,
            @RequestBody UserModel userData) {
        // TODO - Get app Id from appSecret
        String appId = "abc";
        ApplicationModel selectedApp = applicationRepository.findById(appId);
        userData.setApplicationModel(selectedApp);
        userRepository.save(userData);

        return userData;
    }
}
