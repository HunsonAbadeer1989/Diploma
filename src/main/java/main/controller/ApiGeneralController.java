package main.controller;

import com.sun.istack.NotNull;
import main.api.request.CommentRequest;
import main.api.request.EditProfileRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.response.InitResponse;
import main.api.response.ResponseApi;
import main.service.CommentService;
import main.service.PostService;
import main.service.TagService;
import main.service.impl.SettingsServiceImpl;
import main.service.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    @Autowired
    private final InitResponse initResponse;
    @Autowired
    private final SettingsServiceImpl settingsService;
    @Autowired
    private final TagService tagService;
    @Autowired
    private final PostService postService;
    @Autowired
    private final UserProfileServiceImpl editUserProfileService;
    @Autowired
    private final CommentService commentService;

    public ApiGeneralController(InitResponse initResponse,
                                SettingsServiceImpl settingsService,
                                TagService tagService,
                                PostService postService,
                                UserProfileServiceImpl editUserProfileService, CommentService commentService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
        this.editUserProfileService = editUserProfileService;
        this.commentService = commentService;
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    private ResponseEntity<ResponseApi> settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping(value = ("/tag"), params = {"query"})
    public ResponseEntity<ResponseApi> getTagList(@Param(value= "query") String query) {
        return tagService.getTagList(query);
    }

    @GetMapping(value = ("/tag"))
    public ResponseEntity<ResponseApi> getTagListWithoutQuery() {
        return tagService.getTagListWithoutQuery();
    }

    @PostMapping(value = ("/moderation"))
    private ResponseEntity<ResponseApi> moderationOfPost(@RequestBody ModerationOfPostRequest moderationOfPostRequest, HttpServletRequest httpServletRequest) {
        return postService.moderationOfPost(moderationOfPostRequest, httpServletRequest);
    }

    @GetMapping(value = ("/calendar"), params = {"year"})
    private ResponseEntity<ResponseApi> calendarOfPosts(@RequestParam(value = "year") Integer year) {
        return postService.calendarOfPosts(year);
    }

    @PostMapping(value = ("/profile/my"))
    private ResponseEntity<ResponseApi> editMyProfile(@RequestBody EditProfileRequest editProfileRequest, Principal principal) {
        return editUserProfileService.editMyProfile(editProfileRequest, principal);
    }

    @GetMapping(value = "/statistics/my")
    private ResponseEntity<ResponseApi> getMyStatistic(Principal principal){
        return editUserProfileService.getMyStatistic(principal);
    }

    @GetMapping(value = "/statistics/all")
    private ResponseEntity<ResponseApi> getAllStatistic(){
        return editUserProfileService.getAllStatistic();
    }

    @PostMapping(value = "/comment")
    private ResponseEntity<ResponseApi> postComment(@RequestBody @NotNull CommentRequest commentRequest,
                                                    Principal principal){
        return commentService.postComment(commentRequest, principal);
    }


}
