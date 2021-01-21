package main.api.response;

import lombok.Data;

import java.util.List;

@Data
public class AddPostResponse implements ResponseApi {
    private boolean result;
    private List<String> errors;

}
