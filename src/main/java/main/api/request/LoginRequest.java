package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.api.response.RequestApi;

public class LoginRequest implements RequestApi {

    @JsonProperty("e_mail")
    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
