package main.service;

import main.api.request.AddPostRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.response.ResponseApi;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface PostService {

    ResponseEntity<ResponseApi> getPostsWithParams(String mode, Pageable page);

    ResponseEntity<ResponseApi> getPostsByQuery(String query, Pageable page);

    ResponseEntity<ResponseApi> getPostsByDate(String date, Pageable page);

    ResponseEntity<ResponseApi> getPostsByTag(int offset, int limit, String tag);

    ResponseEntity<ResponseApi> getPostsForModeration(int offset, int limit, String status, Principal principal);

    ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status, Principal principal);

    ResponseEntity<ResponseApi> getPostById(long id, Principal principal);

    ResponseEntity<ResponseApi> addPost(AddPostRequest addPostRequest, Principal principal);

    ResponseEntity<ResponseApi> updatePost(long id, AddPostRequest addPostRequest, Principal principal);

    ResponseEntity<ResponseApi> votePost(VotesRequest votesRequest, Principal principal);

    ResponseEntity<ResponseApi> moderationOfPost(ModerationOfPostRequest moderationOfPostRequest, Principal principal);

    ResponseEntity<ResponseApi> calendarOfPosts(Integer year);



}
