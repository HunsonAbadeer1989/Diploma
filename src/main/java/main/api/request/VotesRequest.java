package main.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotesRequest implements RequestApi {

    @JsonProperty("post_id")
    private long postId;

    @JsonIgnore
    private boolean like;

}
