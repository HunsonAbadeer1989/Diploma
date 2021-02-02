package main.service;

import main.api.request.EditProfileRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public interface UserProfileService {

    ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest, Principal principal);

    ResponseEntity<ResponseApi> getMyStatistic(Principal principal);

    ResponseEntity<ResponseApi> getAllStatistic();

}
