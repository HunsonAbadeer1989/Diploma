package main.api.request;

import main.api.response.RequestApi;
import main.model.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class AddPostRequest implements RequestApi {

    private LocalDateTime timestamp;
    private int active;
    private String title;
    private List<Tag> tags;
    private String text;

    public AddPostRequest(LocalDateTime timestamp, int active, String title, List<Tag> tags, String text) {
        this.timestamp = timestamp;
        this.active = active;
        this.title = title;
        this.tags = tags;
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
