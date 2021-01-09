package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginResponse implements ResponseApi {
    private boolean result;
    @JsonProperty("user")
    private UserLoginResponse userLoginResponse;
}
