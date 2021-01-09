package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddPostRequest implements RequestApi {

    private LocalDateTime timestamp;
    private int active;
    private String title;
    private List<Tag> tags;
    private String text;

}
