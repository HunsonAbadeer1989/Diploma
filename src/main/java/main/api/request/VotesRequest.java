package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import main.api.response.RequestApi;

public class VotesRequest implements RequestApi {
    @JsonProperty("post_id")
    private long postId;

    public VotesRequest(long postId) {
        this.postId = postId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
