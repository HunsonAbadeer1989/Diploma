package main.api.response;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CaptchaResponse implements ResponseApi {

    private String secret;
    private String image;

    public CaptchaResponse(String secretCode, String imageBase64) {
        secret = secretCode;
        image = imageBase64;
    }

}
