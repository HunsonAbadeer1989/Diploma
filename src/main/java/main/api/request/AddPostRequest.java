package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPostRequest implements RequestApi {

    private long timestamp;
    private int active;
    private String title;
    private List<String> tags;
    private String text;

}
