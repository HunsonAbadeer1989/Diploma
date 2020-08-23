package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGeneralController {

    @GetMapping("/api/init")
    public String getMainPage(){
        return "index";
    }

    @GetMapping("/api/tag")
    public String fakeTag() {
        return "index";
    }

    @GetMapping("/api/settings")
    public String fakeSetting() {
        return "index";
    }


}
