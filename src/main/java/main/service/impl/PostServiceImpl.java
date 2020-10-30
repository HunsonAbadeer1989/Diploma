package main.service.impl;

import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.request.AddPostRequest;
import main.service.PostService;
import main.api.response.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostService postService;

    @Override
    public ResponseEntity<ResponseApi> getPostsWithParams(int offset, int limit, String mode) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByQuery(int offset, int limit, String query) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByDate(int offset, int limit, LocalDate date) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsByTag(int offset, int limit, String tag) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostsForModeration(int offset, int limit, String status) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> getPostById(long id) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> addPost(AddPostRequest addPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> updatePost(AddPostRequest addPostRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> likePost(VotesRequest votesRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> dislikePost(VotesRequest votesRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> moderationOfPost(ModerationOfPostRequest moderationOfPostRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseApi> calendarOfPosts(Integer year) {
        return null;
    }


}
