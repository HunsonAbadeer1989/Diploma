package main.service.interfaces;

import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

public interface TagRepoService extends ResponseApi {

    public ResponseEntity<ResponseApi> getTagList();
}
