package main.service.impl;

import main.api.response.ResponseApi;
import main.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagServiceImpl implements TagService {

    @Override
    public ResponseEntity<ResponseApi> getTagList(String query) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getTagListWithoutQuery() {
        return null;
    }
}
