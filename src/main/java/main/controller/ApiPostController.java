package main.controller;

import main.api.request.AddPostRequest;
import main.api.request.VotesRequest;
import main.api.response.ResponseApi;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getPostById(@PathVariable(value = "id") long id){
        return postService.getPostById(id);
    }

    @GetMapping(value = "/byTag")
    public ResponseEntity<ResponseApi> searchPostsByTag(@RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam String tag){
        return postService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping(value="")
    public ResponseEntity<ResponseApi> getPosts(@RequestParam(defaultValue = "0") int offset,
                                                @RequestParam(defaultValue = "10") int limit,
                                                @RequestParam(required = false, defaultValue = "recent") String mode) {
        return postService.getPostsWithParams(mode, PageRequest.of(offset / limit, limit));
    }

    @GetMapping(value = "/search")
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<ResponseApi> searchPostsByQuery(@RequestParam(defaultValue = "0") int offset,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestParam(required = false) String query){
        return postService.getPostsByQuery(query, PageRequest.of(offset/limit, limit));
    }

    @GetMapping(value = "/byDate", params = {"offset", "limit", "date"})
    public ResponseEntity<ResponseApi> searchPostsByDate(@RequestParam int offset,
                                                          @RequestParam int limit,
                                                          @RequestParam String date){
        return postService.getPostsByDate(date, PageRequest.of(offset/limit, limit));
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
