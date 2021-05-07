package main.api.response;

import lombok.Data;

@Data
public class NotFoundOrBadRequestResponse implements ResponseApi {

    private String message;

    public NotFoundOrBadRequestResponse(String message) {
        this.message = message;
    }

    public NotFoundOrBadRequestResponse() {

    }
}
