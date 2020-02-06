package hyperdew.authservice.authController;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ToBeDeletedController {

    @GetMapping("/")
    public String getMapping() {
        return "Inside Auth Ms";
    }
}
