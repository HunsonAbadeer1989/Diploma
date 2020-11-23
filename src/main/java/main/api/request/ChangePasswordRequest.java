package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChangePasswordRequest implements RequestApi {

    private String code;
    private String password;
    private String capthca;
    @JsonProperty(value="captcha_secret")
    private String captchaSecret;

    public ChangePasswordRequest(String code, String password, String capthca, String captchaSecret) {
        this.code = code;
        this.password = password;
        this.capthca = capthca;
        this.captchaSecret = captchaSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCapthca() {
        return capthca;
    }

    public void setCapthca(String capthca) {
        this.capthca = capthca;
    }

    public String getCaptchaSecret() {
        return captchaSecret;
    }

    public void setCaptchaSecret(String captchaSecret) {
        this.captchaSecret = captchaSecret;
    }
}
