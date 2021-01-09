package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest implements RequestApi {

    private String code;
    private String password;
    private String capthca;
    @JsonProperty(value="captcha_secret")
    private String captchaSecret;

}
