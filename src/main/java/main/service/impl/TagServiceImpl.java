package main.service.impl;

import main.api.response.ResponseApi;
import main.api.response.TagsResponse;
import main.model.Tag;
import main.repository.TagRepository;
import main.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public ResponseEntity<ResponseApi> getTagList(String query) {
        if (query == null || query.equals("") || query.isBlank()) {
            return getTagListWithoutQuery();
        } else {
            List<Tag> queriedTags = tagRepository.getAllTagsListByQuerySortedByIdDesc(query);
            return getResponseEntityByTagsList(queriedTags);
        }
    }

    @Override
    public ResponseEntity<ResponseApi> getTagListWithoutQuery() {
        List<Tag> allTags = tagRepository.getAllTagsListSortedByIdDesc();
        return getResponseEntityByTagsList(allTags);
    }

    @Override
    public Tag addTag(Tag tag) {
        if (tag == null) {
            return null;
        } else {
            return tagRepository.save(tag);
        }
    }

    private ResponseEntity<ResponseApi> getResponseEntityByTagsList(List<Tag> allTags) {
        HashMap<String, Double> queryTagsMap = new HashMap<>();
        if (!allTags.isEmpty()) {
            Integer mostFrequentTagCount = tagRepository.getMaxTagCount();
            for (Tag tag : allTags) {
                Double weight =  ((double) tagRepository.getTagCountByTagId(tag.getId()) / (double) mostFrequentTagCount);
                queryTagsMap.put(tag.getName(), weight);
            }
            return new ResponseEntity<>(new TagsResponse(queryTagsMap), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
    }
}
