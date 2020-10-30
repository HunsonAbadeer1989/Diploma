package main.service;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest);

    ResponseEntity<ResponseApi> restorePassword(String email);

    ResponseEntity<ResponseApi> changePassword(ChangePasswordRequest changePasswordRequest);

    ResponseEntity<ResponseApi> registerUser(RegisterRequest registerRequest);

    ResponseEntity<ResponseApi> generateCaptcha();

    ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest);
}
