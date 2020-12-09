package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DocumentNotFoundResponse implements ResponseApi {

    private String message;

    public DocumentNotFoundResponse(String message) {
        this.message = message;
    }
}
