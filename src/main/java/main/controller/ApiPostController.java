package main.controller;

import com.sun.istack.NotNull;
import main.api.request.AddPostRequest;
import main.api.request.VotesRequest;
import main.api.response.ResponseApi;
import main.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/post")
public class ApiPostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi> getPostById(@PathVariable(value = "id") long id) {
        return postService.getPostById(id);
    }

    @GetMapping(value = "/byTag")
    public ResponseEntity<ResponseApi> searchPostsByTag(@RequestParam(defaultValue = "0") int offset,
                                                        @RequestParam(defaultValue = "10") int limit,
                                                        @RequestParam String tag) {
        return postService.getPostsByTag(offset, limit, tag);
    }

    @GetMapping(value = "")
    public ResponseEntity<ResponseApi> getPosts(@RequestParam(defaultValue = "0") int offset,
                                                @RequestParam(defaultValue = "10") int limit,
                                                @RequestParam(required = false, defaultValue = "recent") String mode) {
        return postService.getPostsWithParams(mode, PageRequest.of(offset / limit, limit));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<ResponseApi> searchPostsByQuery(@RequestParam(defaultValue = "0") int offset,
                                                          @RequestParam(defaultValue = "10") int limit,
                                                          @RequestParam(required = false) String query) {
        return postService.getPostsByQuery(query, PageRequest.of(offset / limit, limit));
    }

    @GetMapping(value = "/byDate", params = {"offset", "limit", "date"})
    public ResponseEntity<ResponseApi> searchPostsByDate(@RequestParam int offset,
                                                         @RequestParam int limit,
                                                         @RequestParam String date) {
        return postService.getPostsByDate(date, PageRequest.of(offset / limit, limit));
    }

    @GetMapping(value = "/moderation", params = {"offset", "limit", "status"})
    @PreAuthorize("hasAuthority('user:moderate')")
    public ResponseEntity<ResponseApi> getPostsForModeration(@RequestParam(defaultValue = "0") int offset,
                                                             @RequestParam(defaultValue = "10") int limit,
                                                             @RequestParam(defaultValue = "new", required = false) String status,
                                                             @NotNull Principal principal) {
        return postService.getPostsForModeration(offset, limit, status, principal);
    }

    @GetMapping(value = "/my", params = {"offset", "limit", "status"})
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<ResponseApi> getMyPosts(@RequestParam(defaultValue = "0") int offset,
                                                  @RequestParam(defaultValue = "10") int limit,
                                                  @RequestParam(defaultValue = "published") String status,
                                                  @NotNull Principal principal) {
        return postService.getMyPosts(offset, limit, status, principal);
    }

    @PostMapping(value = "")
    public ResponseEntity<ResponseApi> addPost(@RequestBody AddPostRequest addPostRequest,
                                               Principal principal) {
        return postService.addPost(addPostRequest, principal);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ResponseApi> updatePost(
            @PathVariable("id") long id,
            @RequestBody AddPostRequest addPostRequest,
            Principal principal) {
        return postService.updatePost(id, addPostRequest, principal);
    }

    @PostMapping(value = "/like")
    public ResponseEntity<ResponseApi> like(@RequestBody VotesRequest votesRequest, Principal principal) {
        votesRequest.setLike(true);
        return postService.votePost(votesRequest, principal);
    }

    @PostMapping(value = "/dislike")
    public ResponseEntity<ResponseApi> dislike(@RequestBody VotesRequest votesRequest, Principal principal) {
        return postService.votePost(votesRequest, principal);
    }

}
