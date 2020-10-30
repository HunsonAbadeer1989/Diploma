package main.service;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;

public interface UserProfileService {

    ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> getMyStatistic(HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> getAllStatistic(HttpServletRequest httpServletRequest);

}
