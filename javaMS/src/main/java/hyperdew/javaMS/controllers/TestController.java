package hyperdew.javaMS.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testDirect")
    public String myTestController() {
        return "Direct Test Result";
    }
}
