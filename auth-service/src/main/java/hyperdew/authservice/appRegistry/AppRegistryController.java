package hyperdew.authservice.appRegistry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/application")
public class AppRegistryController {

    @Autowired
    private ApplicationRepository applicationRepository;

    //TODO - Only Admin should be allowed to allowed
    @PostMapping
    public ApplicationModel createNewApp(@RequestBody ApplicationModel appModel) {
        applicationRepository.save(appModel);
        return appModel;
    }

}
