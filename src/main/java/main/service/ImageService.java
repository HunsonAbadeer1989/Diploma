package main.service;

import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    ResponseEntity<ResponseApi> uploadImage(MultipartFile image) throws Exception;

    String uploadUserPhoto(MultipartFile photo) throws Exception;

}
