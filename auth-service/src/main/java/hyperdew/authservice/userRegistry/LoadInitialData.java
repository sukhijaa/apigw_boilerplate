package hyperdew.authservice.userRegistry;


import hyperdew.authservice.utils.Constants;
import hyperdew.authservice.utils.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoadInitialData implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserModel adminUser = new UserModel();
        adminUser.setDisplayName("Root Admin User");
        adminUser.setUserName(Constants.ADMIN_USER_NAME);
        adminUser.setPassword(passwordEncoder.encode(Constants.ADMIN_USER_PASSWORD));
        adminUser.setUserRole(Roles.ADMIN_ROLE);

        userRepository.save(adminUser);

        System.out.println("Admin User Saved");
    }
}
