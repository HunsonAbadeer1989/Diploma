package main.service.impl;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import main.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Override
    public ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest,
                                                     HttpServletRequest httpServletRequest) {
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
