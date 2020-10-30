package main.service.impl;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.ResponseApi;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest) {
        return null;
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
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> generateCaptcha() {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        return null;
    }

}
