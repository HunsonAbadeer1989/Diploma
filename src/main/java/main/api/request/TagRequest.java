package main.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Tag;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TagRequest implements RequestApi {

    private List<Tag> tags;

    public TagRequest(List<String> listOfTags){
        tags = new ArrayList<>();
        for(String t : listOfTags){
            Tag tag = new Tag(t);
            tags.add(tag);
        }
    }

}
