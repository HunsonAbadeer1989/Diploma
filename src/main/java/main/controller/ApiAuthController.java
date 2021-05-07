package main.controller;

import com.sun.istack.NotNull;
import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.CheckResponse;
import main.api.response.LoginResponse;
import main.api.response.ResponseApi;
import main.api.response.UserLoginResponse;
import main.repository.UserRepository;
import main.service.AuthService;
import main.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    public final UserRepository userRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private CaptchaService capthcaService;

    public ApiAuthController( UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApi> loginUser(@RequestBody LoginRequest loginRequest) {
        return authService.loginUser(loginRequest);
    }

    @GetMapping(value = "/check")
    public ResponseEntity<ResponseApi> check(Principal principal) {
        return authService.check(principal);
    }

    @PostMapping(value = "/restore", params = {"email"})
    public ResponseEntity<ResponseApi> restorePassword(@RequestParam String email) {
        return authService.restorePassword(email);
    }

    @PostMapping(value = "/password")
    public ResponseEntity<ResponseApi> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseApi> registerUser(@NotNull @RequestBody RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @GetMapping(value = ("/captcha"))
    public ResponseEntity<ResponseApi> generateCaptcha() {
        return capthcaService.generateCaptcha();
    }

    @GetMapping(value = "/logout")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        SecurityContextHolder.clearContext();
        return new ResponseEntity(new CheckResponse(true), HttpStatus.OK);
    }


}
