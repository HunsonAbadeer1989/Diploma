package main.service;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

public interface AuthService {

    ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest);

    ResponseEntity<ResponseApi> restorePassword(String email);

    ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest);

    ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest);

    ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> check(Principal principal);
}
