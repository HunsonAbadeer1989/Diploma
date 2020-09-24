package main.service.interfaces;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserProfileRepoService {

    ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> getMyStatistic(HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> getAllStatistic(HttpServletRequest httpServletRequest);
}
