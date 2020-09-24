package main.service.interfaces;

import main.api.request.ModerationOfPostRequest;
import main.api.request.VotesRequest;
import main.api.request.AddPostRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;

public interface PostRepoService {

    ResponseEntity<ResponseApi> getPostsWithParams(int offset, int limit, String mode);

    ResponseEntity<ResponseApi> getPostsByQuery(int offset, int limit, String query);

    ResponseEntity<ResponseApi> getPostsByDate(int offset, int limit, LocalDate date);

    ResponseEntity<ResponseApi> getPostsByTag(int offset, int limit, String tag);

    ResponseEntity<ResponseApi> getPostsForModeration(int offset, int limit, String status);

    ResponseEntity<ResponseApi> getMyPosts(int offset, int limit, String status);

    ResponseEntity<ResponseApi> getPostById(long id);

    ResponseEntity<ResponseApi> addPost(AddPostRequest addPostRequest);

    ResponseEntity<ResponseApi> updatePost(AddPostRequest addPostRequest);

    ResponseEntity<ResponseApi> likePost(VotesRequest votesRequest);

    ResponseEntity<ResponseApi> dislikePost(VotesRequest votesRequest);

    ResponseEntity<ResponseApi> moderationOfPost(ModerationOfPostRequest moderationOfPostRequest, HttpServletRequest httpServletRequest);

    ResponseEntity<ResponseApi> calendarOfPosts(Integer year);
}
