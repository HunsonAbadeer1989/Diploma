package main.controller;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.ResponseApi;
import main.service.interfaces.AuthRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class ApiAuthController {

    @Autowired
    private AuthRepoService authRepoService;

    @PostMapping(value = "/api/auth/login")
    public ResponseEntity<ResponseApi> loginUser(@RequestBody LoginRequest loginRequest){
        return  authRepoService.loginUser(loginRequest);
    }

    @GetMapping(value = "/api/auth/check")
    public ResponseEntity<ResponseApi> checkUser(HttpSession session){
        return null;
    }

    @PostMapping(value = "/api/auth/restore", params = {"email"})
    public ResponseEntity<ResponseApi> restorePassword(@RequestParam String email){
        return authRepoService.restorePassword(email);
    }

    @PostMapping(value = "/api/auth/password")
    public ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest){
        return authRepoService.changePassword(changePasswordRequest);
    }

    @PostMapping(value = "/api/auth/register")
    public  ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest ){
        return authRepoService.registerUser(registerRequest);
    }

    @GetMapping(value=("/api/auth/captcha"))
    public ResponseEntity<ResponseApi> generateCaptcha(){
        return authRepoService.generateCaptcha();
    }

    @GetMapping(value="/api/auth/logout")
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest){
        return authRepoService.logout(httpServletRequest);
    }
}
