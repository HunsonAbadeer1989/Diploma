package main.api.response;

import lombok.*;

import java.util.HashMap;

@Data
@NoArgsConstructor
public class RegisterResponse implements ResponseApi {
    private boolean result;
    private HashMap<String, String> errors;

    public RegisterResponse(boolean result) {
        this.result = result;
    }
}
