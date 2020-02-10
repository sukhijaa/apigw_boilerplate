package hyperdew.authservice.userRegistry;

import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.appRegistry.ApplicationRepository;
import hyperdew.authservice.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private UserRepository userRepository;

    // TODO - Model as per dynamic Role creating
    @PostMapping
    @RolesAllowed(Roles.ADMIN_ROLE)
    public UserModel createUser(
            @RequestHeader("app-secret") String appSecret,
            @RequestBody UserModel userData) {
        // TODO - Get app Id from appSecret
        long appId = 0;
        ApplicationModel selectedApp = applicationRepository.findById(appId);
        userData.setApplicationModel(selectedApp);
        userRepository.save(userData);

        return userData;
    }
}
