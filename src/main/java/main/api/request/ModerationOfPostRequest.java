package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModerationOfPostRequest implements RequestApi {

    @JsonProperty("post_id")
    @NonNull
    private long postId;

    @NonNull
    private String decision;

}
