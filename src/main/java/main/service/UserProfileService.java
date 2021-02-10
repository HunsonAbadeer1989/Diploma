package main.service;

import main.api.request.EditProfileRequest;
import main.api.request.EditProfileWithPhotoRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface UserProfileService {

    ResponseEntity<ResponseApi> editMyProfile(EditProfileRequest editProfileRequest, Principal principal);

    ResponseEntity<ResponseApi> editMyProfileWithPhoto(MultipartFile photo,
                                                       String name,
                                                       String email,
                                                       String password,
                                                       Principal principal) throws Exception;

    ResponseEntity<ResponseApi> getMyStatistic(Principal principal);

    ResponseEntity<ResponseApi> getAllStatistic();

    Object uploadImage(MultipartFile image, String folder) throws Exception;

}
