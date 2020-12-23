package main.controller;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.CheckResponse;
import main.api.response.ResponseApi;
import main.service.AuthService;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private CaptchaService capthcaService;

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseApi> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest httpServletRequest){
        return  authService.loginUser(loginRequest, httpServletRequest.getSession());
    }

    @GetMapping(value = "/check")
    public ResponseEntity<ResponseApi> checkUser(HttpSession session){
        return ResponseEntity.ok(new CheckResponse());
    }

    @PostMapping(value = "/restore", params = {"email"})
    public ResponseEntity<ResponseApi> restorePassword(@RequestParam String email){
        return authService.restorePassword(email);
    }

    @PostMapping(value = "/password")
    public ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest){
        return authService.changePassword(changePasswordRequest);
    }

    @PostMapping(value = "/register")
    public  ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest ){
        return authService.registerUser(registerRequest);
    }

    @GetMapping(value=("/captcha"))
    public ResponseEntity<ResponseApi> generateCaptcha(){
        return capthcaService.generateCaptcha();
    }

    @GetMapping(value="/logout")
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest){
        return authService.logout(httpServletRequest);
    }
}
