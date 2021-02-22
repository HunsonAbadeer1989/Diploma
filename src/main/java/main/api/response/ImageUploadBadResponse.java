package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageUploadBadResponse implements ResponseApi{

    private boolean result;
    private HashMap<String, String> errors;

}
