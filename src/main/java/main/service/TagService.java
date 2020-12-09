package main.service;

import main.api.response.ResponseApi;
import main.model.Tag;
import org.springframework.http.ResponseEntity;

public interface TagService extends ResponseApi {

    ResponseEntity<ResponseApi> getTagList(String query);

    ResponseEntity<ResponseApi> getTagListWithoutQuery();

    Tag addTag(Tag tag);

}
