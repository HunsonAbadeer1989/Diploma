package main.controller;

import main.api.request.VotesRequest;
import main.api.request.AddPostRequest;
import main.service.interfaces.PostRepoService;
import main.api.response.ResponseApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
public class ApiPostController {

    @Autowired
    private PostRepoService postRepoService;

    @GetMapping(value = "/api/post", params = {"offset", "limit", "mode"})
    public ResponseEntity<ResponseApi> getPosts(@RequestParam(value = "offset") int offset,
                                                @RequestParam(value = "limit") int limit,
                                                @RequestParam(value = "mode") String mode){
        return postRepoService.getPostsWithParams(offset, limit, mode);
    }

    @GetMapping(value = "/api/post/search", params = {"offset", "limit", "query"})
    public ResponseEntity<ResponseApi> searchPostsByQuery(@RequestParam int offset,
                                                   @RequestParam int limit,
                                                   @RequestParam String query){
        return postRepoService.getPostsByQuery(offset, limit, query);
    }

    @GetMapping(value = "/api/post/byDate", params = {"offset", "limit", "date"})
    public ResponseEntity<ResponseApi> searchPostsByDate(@RequestParam int offset,
                                                          @RequestParam int limit,
                                                          @RequestParam LocalDate date){
        return postRepoService.getPostsByDate(offset, limit, date);
    }

    @GetMapping(value = "/api/post/byTag", params = {"offset", "limit", "tag"})
    public ResponseEntity<ResponseApi> searchPostsByTag(@RequestParam int offset,
                                                         @RequestParam int limit,
                                                         @RequestParam String tag){
        return postRepoService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping(value = "/api/post/moderation", params = {"offset", "limit", "status"})
    public ResponseEntity<ResponseApi> getPostsForModeration(@RequestParam int offset,
                                                             @RequestParam int limit,
                                                             @RequestParam String status){
        return postRepoService.getPostsForModeration(offset, limit, status);
    }

    @GetMapping(value = "/api/post/my", params = {"offset", "limit", "status"})
    public ResponseEntity<ResponseApi> getMyPosts(@RequestParam int offset,
                                                  @RequestParam int limit,
                                                  @RequestParam String status){
        return postRepoService.getMyPosts(offset, limit, status);
    }

    @GetMapping(value = "/api/post/{id}")
    public ResponseEntity<ResponseApi> getPostById(@PathVariable(value = "id") long id){
        return postRepoService.getPostById(id);
    }

    @PostMapping(value = "/api/post")
    public ResponseEntity<ResponseApi> addPost(@RequestParam AddPostRequest addPostRequest){
        return postRepoService.addPost(addPostRequest);
    }

    @PutMapping(value = "/api/post/{id}")
    public ResponseEntity<ResponseApi> updatePost(@RequestParam AddPostRequest addPostRequest){
        return postRepoService.updatePost(addPostRequest);
    }

    @PostMapping(value = "/api/post/like")
    public ResponseEntity<ResponseApi> likePost(@RequestBody VotesRequest votesRequest){
        return postRepoService.likePost(votesRequest);
    }

    @PostMapping(value = "/api/post/dislike")
    public ResponseEntity<ResponseApi> dislike(@RequestBody VotesRequest votesRequest){
        return postRepoService.dislikePost(votesRequest);
    }



}
