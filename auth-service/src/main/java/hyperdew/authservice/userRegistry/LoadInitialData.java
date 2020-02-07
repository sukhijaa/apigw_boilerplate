package hyperdew.authservice.userRegistry;


import hyperdew.authservice.appRegistry.ApplicationModel;
import hyperdew.authservice.appRegistry.ApplicationRepository;
import hyperdew.authservice.securityConfig.JWTTokenUtil;
import hyperdew.authservice.utils.Constants;
import hyperdew.authservice.utils.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoadInitialData implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(LoadInitialData.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTTokenUtil jwtTokenUtil;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserModel adminUser = new UserModel();
        adminUser.setDisplayName("Root Admin User");
        adminUser.setUserName(Constants.ADMIN_USER_NAME);
        adminUser.setPassword(passwordEncoder.encode(Constants.ADMIN_USER_PASSWORD));
        adminUser.setUserRole(Roles.ADMIN_ROLE);

        userRepository.save(adminUser);
        logger.info("Admin User Saved in DB");

        ApplicationModel appModel = new ApplicationModel();
        appModel.setApplicationDisplayName("Dummy Application");
        appModel.setApplicationName("dummyApp");

        applicationRepository.save(appModel);
        logger.info("Dummy App Inserted in DB for Dev Purposes");


        // Add a GUEST user for appModel
        UserModel guestUser = new UserModel();
        guestUser.setDisplayName("Guest User");
        guestUser.setUserName(Constants.GUEST_USER_NAME);
        guestUser.setUserRole(Roles.GUEST_ROLE);
        guestUser.setApplicationModel(appModel);

        userRepository.save(guestUser);
        logger.info("Guest User added for Dummy App");

        logger.info("Dummy App Secret : " + jwtTokenUtil.generateToken(appModel, Constants.GUEST_USER_EXPIRY));
    }
}
