package main.service.impl;

import main.api.request.ChangePasswordRequest;
import main.api.request.LoginRequest;
import main.api.request.RegisterRequest;
import main.api.response.ResponseApi;
import main.repository.PostRepository;
import main.repository.UserRepository;
import main.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    @Override
    public ResponseEntity<ResponseApi> loginUser(LoginRequest loginRequest, HttpSession session) {
        String email = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//        User user = userRepository.getUserByEmail(email);
//        if (user == null) {
//            return new ResponseEntity<>(
//                    new BadRequestResponse("e-mail: "
//                            + email + " не существует"),
//                    HttpStatus.BAD_REQUEST);
//        }
//        String hashedPassToCheck = getHashedString(password);
//        if (user.getStoredHashPass().equals(hashedPassToCheck)) {
//            sessionIdToUserId.put(session.getId().toString(), user.getId());
//            return new ResponseEntity<>(new LoginResponse(user, postRepository.countPostsForModeration()), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(
//                    new BadRequestResponse("Пароль введен не верно"),
//                    HttpStatus.BAD_REQUEST);
//        }
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
    public ResponseEntity<ResponseApi> logout(HttpServletRequest httpServletRequest) {
        return null;
    }

}
