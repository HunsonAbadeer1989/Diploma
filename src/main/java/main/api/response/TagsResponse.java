package main.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Data

public class TagsResponse implements ResponseApi {

    private List<TagWithWeight> tags;

    public TagsResponse(HashMap<String, Double> queryTagsMap) {
        tags = new LinkedList<>();
        for (String key : queryTagsMap.keySet()) {
            TagWithWeight tagWithWeight = new TagWithWeight(key, queryTagsMap.get(key));
            tags.add(tagWithWeight);
        }
    }

    @Data
    @AllArgsConstructor
    static class TagWithWeight {

        private String name;
        private double weight;

    }

}
