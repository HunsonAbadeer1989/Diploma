package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiPostController {

    @GetMapping("/api/post")
    public String fakePost() {
        return "index";
    }

}
