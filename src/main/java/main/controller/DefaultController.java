package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class DefaultController {

    @GetMapping("/api/init")
    public String getMainPage(){
        return "index";
    }

    @GetMapping("/api/auth/check")
    public String zaglushkaAuth() {
        return "index";
    }

    @GetMapping("/api/settings")
    public String zaglushkaSetting() {
        return "index";
    }

    @GetMapping("/api/tag")
    public String zaglushkaTag() {
        return "index";
    }

    @GetMapping("/api/post")
    public String zaglushkaPost() {
        return "index";
    }
}
