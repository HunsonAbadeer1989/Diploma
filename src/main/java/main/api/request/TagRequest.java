package main.api.request;

import main.model.Tag;

import java.util.List;

public class TagRequest implements RequestApi {

    private List<Tag> tags;

    public TagRequest(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
