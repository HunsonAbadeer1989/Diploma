package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAuthController {

    @RequestMapping("/api/auth/check")
    public String fakeAuth() {
        return "index";
    }

}
