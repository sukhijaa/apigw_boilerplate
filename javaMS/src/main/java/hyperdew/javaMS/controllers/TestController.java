package hyperdew.javaMS.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testDirect")
    public String myTestController(@RequestHeader(value = "access-token", required = false) String accessToken) {

        return "Direct Test Result " + accessToken;
    }
}
