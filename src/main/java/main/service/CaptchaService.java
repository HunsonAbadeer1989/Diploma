package main.service;

import main.api.response.ResponseApi;
import main.model.CaptchaCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public interface CaptchaService {

    ResponseEntity<ResponseApi> generateCaptcha();

    ArrayList<CaptchaCode> getAllCaptchas();

}
