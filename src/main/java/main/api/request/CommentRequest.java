package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CommentRequest implements RequestApi {
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("post_id")
    private Long postId;
    private String text;


}
