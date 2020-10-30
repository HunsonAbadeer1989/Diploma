package main.service;

import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

public interface TagService extends ResponseApi {

    ResponseEntity<ResponseApi> getTagList(String query);

    ResponseEntity<ResponseApi> getTagListWithoutQuery();


}
