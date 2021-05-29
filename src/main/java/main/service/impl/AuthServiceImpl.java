package main.service.impl;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.*;
import main.config.ScheduledConfig;
import main.config.SecurityConfig;
import main.model.CaptchaCode;
import main.repository.CaptchaRepository;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Value("${spring.mail.username}")
    private String EMAIL_ADRESS;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SecurityConfig securityConfig;

    @Autowired
    private ScheduledConfig scheduledConfig;

    @Autowired
    private CaptchaRepository captchaRepository;

    public final AuthenticationManager authenticationManager;

    public final JavaMailSender mailSender;

    public final HttpServletRequest servletRequest;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           JavaMailSender mailSender,
                           HttpServletRequest servletRequest) {
        this.authenticationManager = authenticationManager;
        this.mailSender = mailSender;
        this.servletRequest = servletRequest;
    }

    @Override
    public ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest) {
        main.model.User loginUser = userRepository.findByEmail(loginRequest.getEmail());

        if (loginUser == null) {
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setResult(false);
            return ResponseEntity.ok(loginResponse);
        }

        Authentication auth = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
                                loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(auth);
        User user = (User) auth.getPrincipal();
        return ResponseEntity.ok(getLoginResponse(user.getUsername()));
    }

    @Override
    public ResponseEntity<ResponseApi> restorePassword(String email) {
        main.model.User user = userRepository.findByEmail(email);
        if(user == null) {
            return ResponseEntity.ok(new CheckResponse(false));
        }

        String code = UUID.randomUUID().toString().replaceAll("-", "");
        String linkToCode = "/login/change-password/" + code;

        userRepository.updateUserCode(user.getEmail(), code);
        return sendEmail(email, linkToCode);
    }

    private ResponseEntity<ResponseApi> sendEmail(String email, String linkToCode) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;

        StringBuilder rootUrl = new StringBuilder();
        rootUrl.append(servletRequest.getScheme())
                .append("://")
                .append(servletRequest.getServerName())
                .append(":")
                .append(servletRequest.getServerPort());
        String htmlMsg = "To restore your password go to <a href=\"" + rootUrl + linkToCode + "\"><u>link<u/></a>";

        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            mimeMessage.setContent(htmlMsg, "text/html");
            messageHelper.setFrom(EMAIL_ADRESS);
            messageHelper.setTo(email);
            messageHelper.setSubject("Restore password");
        }
        catch(MessagingException ex){
            ex.printStackTrace();
            return ResponseEntity.ok(new CheckResponse(false));

        }

        mailSender.send(mimeMessage);
        return ResponseEntity.ok(new CheckResponse(true));
    }

    @Override
    public ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest chgPassRequest) {
        ResultResponse response = new ResultResponse(false);
        HashMap<String, String> errors = new HashMap<>();
        main.model.User user = userRepository.getUserByCode(chgPassRequest.getCode());

        if(user == null){
            errors.put("code", "Ссылка для восстановления пароля устарела.\n" +
                    "\t<a href=\n" +
                    "\t\\/auth/restore\\>Запросить ссылку снова</a>\"");
        }

        int MIN_PASS_LENGTH = 6;
        if(chgPassRequest.getPassword().length() < MIN_PASS_LENGTH){
            errors.put("password", "Пароль короче 6-ти символов");
        }

        if(!checkCaptchaAndSecretCaptcha(chgPassRequest.getCaptcha(), chgPassRequest.getCaptchaSecret())){
            errors.put("captcha", "Код с картинки введен не верно");
        }

        if(errors.isEmpty()){
            String encodePassword = encodePassword(chgPassRequest.getPassword());
            userRepository.updatePassword(chgPassRequest.getCode(), encodePassword);
            response.setResult(true);
        }
        else{
            response.setResult(false);
            response.setErrors(errors);
        }
        return ResponseEntity.ok(response);
    }

    private boolean checkCaptchaAndSecretCaptcha(String captcha, String captchaSecret) {
        CaptchaCode captchaBySecretCode = captchaRepository.getCaptchaBySecretCode(captchaSecret);
        return captcha.equals(captchaBySecretCode.getCode());
    }

    @Override
    public ResponseEntity<ResponseApi> check(Principal principal) {
        if (principal == null) {
            return ResponseEntity.ok(new LoginResponse());
        }
        return ResponseEntity.ok(getLoginResponse(principal.getName()));
    }

    @Override
    public ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest) {
        RegisterResponse registerResponse = checkRegisterRequestData(registerRequest);

        if (!registerResponse.isResult()) {
            return new ResponseEntity<>(registerResponse, HttpStatus.OK);
        }
        registerResponse = saveNewUser(registerRequest);

        return new ResponseEntity<>(registerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        return null;
    }

    private LoginResponse getLoginResponse(String email) {
        main.model.User currentUser = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setName(currentUser.getName());
        userLoginResponse.setEmail(currentUser.getEmail());
        userLoginResponse.setPhoto(currentUser.getPhoto());
        userLoginResponse.setModeration(currentUser.getIsModerator() == 1);
        userLoginResponse.setId(currentUser.getId());
        userLoginResponse.setSettings(currentUser.getIsModerator() == 1);
        userLoginResponse.setModerationCount(email, postRepository);

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

        if (userByEmailFromRequest != null) {
            registerResponse.setResult(false);
            errors.put("email", "Этот e-mail " + emailFromRequest + " уже зарегистрирован.");
        }

        String nameFromRequest = registerRequest.getName();
        if (nameFromRequest.isEmpty()) {
            registerResponse.setResult(false);
            errors.put("name", "Имя " + nameFromRequest + "  указано неверно.");
        }

        String passwordFromRequest = registerRequest.getPassword();
        if (passwordFromRequest != null && passwordFromRequest.length() < 6) {
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

        String encodePassword = encodePassword(registerRequest.getPassword());
        newUser.setPassword(encodePassword);
        newUser.setName(registerRequest.getName());
        newUser.setRegTime(LocalDateTime.now());
        userRepository.save(newUser);

        return new RegisterResponse(true);
    }

    private String encodePassword(String password) {
        PasswordEncoder encoder = securityConfig.passwordEncoder();
        return encoder.encode(password);
    }

    private boolean checkEqualsCaptches(String captchaFromRequest, String captchaID) {
        CaptchaCode captchaCodeBySecretCode = captchaRepository.getCaptchaBySecretCode(captchaID);

        return captchaCodeBySecretCode.getCode().equals(captchaFromRequest);
    }

}
