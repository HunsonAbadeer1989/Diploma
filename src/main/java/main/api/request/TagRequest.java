package main.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.Tag;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagRequest implements RequestApi {

    private List<Tag> tags;

}
