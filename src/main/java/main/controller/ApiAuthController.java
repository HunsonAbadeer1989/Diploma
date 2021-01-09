package main.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
public class ApiAuthController {

    public final AuthenticationManager authenticationManager;
    public final UserRepository userRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private CaptchaService capthcaService;

    public ApiAuthController(AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseApi> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication auth;

        try {
            auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                    loginRequest.getPassword()));
        }
        catch (AuthenticationException e){
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setResult(true);
            return ResponseEntity.ok(loginResponse);
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(user.getUsername()));
    }

    @GetMapping(value = "/check")
    public ResponseEntity<ResponseApi> check(Principal principal) {
        if(principal == null){
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @PostMapping(value = "/restore", params = {"email"})
    public ResponseEntity<ResponseApi> restorePassword(@RequestParam String email) {
        return authService.restorePassword(email);
    }

    @PostMapping(value = "/password")
    public ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest) {
        return authService.registerUser(registerRequest);
    }

    @GetMapping(value = ("/captcha"))
    public ResponseEntity<ResponseApi> generateCaptcha() {
        return capthcaService.generateCaptcha();
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        return authService.logout(httpServletRequest);
    }

    private LoginResponse getLoginResponse(String email) {
        main.model.User currentUser = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
        userLoginResponse.setId(currentUser.getId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setResult(true);
        loginResponse.setUserLoginResponse(userLoginResponse);
        return loginResponse;
    }
}
