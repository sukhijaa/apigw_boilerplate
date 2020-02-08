package hyperdew.authservice.authController;


import hyperdew.authservice.utils.Roles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/test")
public class ToBeDeletedController {

    @GetMapping
    public String getMapping() {
        return "Inside Auth Ms";
    }

    @GetMapping("/abhi")
    public String getAbhiMapping() {
        return "Inside Test Class";
    }

    @GetMapping("/guest")
    @RolesAllowed(Roles.GUEST_ROLE)
    public String getGuestMapping() {
        return "Guest user accessed it";
    }

    @GetMapping("/admin")
    @RolesAllowed(Roles.ADMIN_ROLE)
    public String getAdminMapping() {
        return "Admin User Accessed it";
    }
}
