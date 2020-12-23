package main.api.response;

import lombok.Data;

@Data
public class CheckResponse implements ResponseApi {
    private boolean result;

    public CheckResponse() {
        this.result = true;
    }
}
