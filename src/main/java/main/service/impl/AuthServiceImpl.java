package main.service.impl;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.LoginResponse;
import main.api.response.RegisterResponse;
import main.api.response.ResponseApi;
import main.api.response.UserLoginResponse;
import main.config.SecurityConfig;
import main.model.CaptchaCode;
import main.repository.CaptchaRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private CaptchaRepository captchaRepository;

    public final AuthenticationManager authenticationManager;

    public AuthServiceImpl(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Override
    public ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest) {
        Authentication auth;

        try {
            auth = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                    loginRequest.getPassword()));
        } catch (AuthenticationException e) {
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
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @Override
    public ResponseEntity<ResponseApi> restorePassword(String email) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = checkRegisterRequestData(registerRequest);

        if (!registerResponse.isResult()) {
            return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
        }
        registerResponse = saveNewUser(registerRequest);

        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        return null;
    }

    private LoginResponse getLoginResponse(String email) {
        main.model.User currentUser = userRepository.findByEmail(email);
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

    private RegisterResponse checkRegisterRequestData(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setResult(true);
        HashMap<String, String> errors = new HashMap<>();

        String emailFromRequest = registerRequest.getEmail();
        main.model.User userByEmailFromRequest = userRepository.findByEmail(emailFromRequest);

        if(userByEmailFromRequest != null){
            registerResponse.setResult(false);
            errors.put("email", "Этот e-mail " + emailFromRequest + " уже зарегистрирован.");
        }

        String nameFromRequest = registerRequest.getName();
        if(nameFromRequest.isEmpty()){
            registerResponse.setResult(false);
            errors.put("name", "Имя " + nameFromRequest + "  указано неверно.");
        }

        String passwordFromRequest = registerRequest.getPassword();
        if(passwordFromRequest != null && passwordFromRequest.length() < 6 ){
            registerResponse.setResult(false);
            errors.put("password", "Пароль короче 6-ти символов");
        }

        String captcha = registerRequest.getCaptcha();
        String captchaID = registerRequest.getCaptchaSecret();
        boolean isEquals = checkEqualsCaptches(captcha, captchaID);

        if (!isEquals) {
            registerResponse.setResult(false);
            errors.put("captcha", "Код с картинки введён неверно.");
        }
        registerResponse.setErrors(errors);
        return registerResponse;
    }

    private RegisterResponse saveNewUser(RegisterRequest registerRequest) {
        main.model.User newUser = new main.model.User();
        newUser.setEmail(registerRequest.getEmail());

        PasswordEncoder encoder = securityConfig.passwordEncoder();
        String encodePassword = encoder.encode(registerRequest.getPassword());
        newUser.setPassword(encodePassword);
        newUser.setName(registerRequest.getName());
        newUser.setRegTime(LocalDateTime.now());
        userRepository.save(newUser);

        return new RegisterResponse(true);
    }

    private boolean checkEqualsCaptches(String captchaFromRequest, String captchaID) {
        CaptchaCode captchaCodeBySecretCode = captchaRepository.getCaptchaBySecretCode(captchaID);

        return captchaCodeBySecretCode.getCode().equals(captchaFromRequest);
    }

}
