package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ModerationOfPostRequest implements RequestApi {

    @JsonProperty("post_id")
    private long postId;
    private String decision;

    public ModerationOfPostRequest(long postId, String decision) {
        this.postId = postId;
        this.decision = decision;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }
}
