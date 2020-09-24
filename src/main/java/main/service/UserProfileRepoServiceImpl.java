package main.service;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import main.service.interfaces.UserProfileRepoService;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public class UserProfileRepoServiceImpl implements UserProfileRepoService {

    @Override
    public ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getMyStatistic(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getAllStatistic(HttpServletRequest httpServletRequest) {
        return null;
    }
}
