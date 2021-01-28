package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;

@Data
@AllArgsConstructor
public class CommentErrorResponse implements ResponseApi {

    private boolean response;
    private HashMap<String, String> errors;

}
