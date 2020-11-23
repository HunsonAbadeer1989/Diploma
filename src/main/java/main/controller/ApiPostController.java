package main.controller;

import main.api.request.AddPostRequest;
import main.api.request.VotesRequest;
import main.api.response.ResponseApi;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PostService postService;

    @GetMapping(params = {"offset", "limit", "mode"})
    public ResponseEntity<ResponseApi> getPosts(@RequestParam(value = "offset",
                                                              required = false,
                                                              defaultValue = "0") int offset,
                                                @RequestParam(value = "limit") int limit,
                                                @RequestParam(value = "mode") String mode){

        return postService.getPostsWithParams(offset, limit, mode);
    }

    @GetMapping(value = "/search", params = {"offset", "limit", "query"})
    public ResponseEntity<ResponseApi> searchPostsByQuery(@RequestParam int offset,
                                                   @RequestParam int limit,
                                                   @RequestParam String query){
        return postService.getPostsByQuery(offset, limit, query);
    }

    @GetMapping(value = "/byDate", params = {"offset", "limit", "date"})
    public ResponseEntity<ResponseApi> searchPostsByDate(@RequestParam int offset,
                                                          @RequestParam int limit,
                                                          @RequestParam LocalDate date){
        return postService.getPostsByDate(offset, limit, date);
    }

    @GetMapping(value = "/byTag", params = {"offset", "limit", "tag"})
    public ResponseEntity<ResponseApi> searchPostsByTag(@RequestParam int offset,
                                                         @RequestParam int limit,
                                                         @RequestParam String tag){
        return postService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping(value = "/moderation", params = {"offset", "limit", "status"})
    public ResponseEntity<ResponseApi> getPostsForModeration(@RequestParam int offset,
                                                             @RequestParam int limit,
                                                             @RequestParam String status){
        return postService.getPostsForModeration(offset, limit, status);
    }

    @GetMapping(value = "/my", params = {"offset", "limit", "status"})
    public ResponseEntity<ResponseApi> getMyPosts(@RequestParam int offset,
                                                  @RequestParam int limit,
                                                  @RequestParam String status){
        return postService.getMyPosts(offset, limit, status);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ResponseApi> getPostById(@PathVariable(value = "id") long id){
        return postService.getPostById(id);
    }

    @GetMapping
    public ResponseEntity<ResponseApi> getPostsByTag(@RequestParam int offset,
                                                     @RequestParam int limit,
                                                     @RequestParam String tag){
        return postService.getPostsByTag(offset, limit, tag);
    }

    @PostMapping
    public ResponseEntity<ResponseApi> addPost(@RequestParam AddPostRequest addPostRequest){
        return postService.addPost(addPostRequest);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseApi> updatePost(@RequestParam AddPostRequest addPostRequest){
        return postService.updatePost(addPostRequest);
    }

    @PostMapping(value = "/like")
    public ResponseEntity<ResponseApi> likePost(@RequestBody VotesRequest votesRequest){
        return postService.likePost(votesRequest);
    }

    @PostMapping(value = "/dislike")
    public ResponseEntity<ResponseApi> dislike(@RequestBody VotesRequest votesRequest){
        return postService.dislikePost(votesRequest);
    }

}
