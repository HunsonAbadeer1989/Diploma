package main.controller;

import com.sun.istack.NotNull;
import main.api.request.CommentRequest;
import main.api.request.EditProfileRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.response.InitResponse;
import main.api.response.ResponseApi;
import main.service.CommentService;
import main.service.ImageService;
import main.service.PostService;
import main.service.TagService;
import main.service.impl.SettingsServiceImpl;
import main.service.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    private final ImageService imageService;

    public ApiGeneralController(InitResponse initResponse,
                                SettingsServiceImpl settingsService,
                                TagService tagService,
                                PostService postService,
                                UserProfileServiceImpl editUserProfileService, CommentService commentService, ImageService imageService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
        this.editUserProfileService = editUserProfileService;
        this.commentService = commentService;
        this.imageService = imageService;
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
    public ResponseEntity<ResponseApi> getTagList(@Param(value = "query") String query) {
        return tagService.getTagList(query);
    }

    @GetMapping(value = ("/tag"))
    public ResponseEntity<ResponseApi> getTagListWithoutQuery() {
        return tagService.getTagListWithoutQuery();
    }

    @PostMapping(value = ("/moderation"))
    private ResponseEntity<ResponseApi> moderationOfPost(@RequestBody ModerationOfPostRequest moderationOfPostRequest,
                                                         Principal principal) {
        return postService.moderationOfPost(moderationOfPostRequest, principal);
    }

    @GetMapping(value = ("/calendar"), params = {"year"})
    private ResponseEntity<ResponseApi> calendarOfPosts(@RequestParam(value = "year") Integer year) {
        return postService.calendarOfPosts(year);
    }

    @PostMapping(value = ("/profile/my"), consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<ResponseApi> editMyProfileWithPhoto(@RequestParam("photo") MultipartFile photo,
                                                               @RequestParam("name") String name,
                                                               @RequestParam("email") String email,
                                                               @RequestParam("password") String password,
                                                               Principal principal) throws Exception {
        return editUserProfileService.editMyProfileWithPhoto(photo, name, email, password, principal);
    }

    @PostMapping(value = ("/profile/my"))
    private ResponseEntity<ResponseApi> editMyProfile(@RequestBody(required = false) EditProfileRequest editProfileRequest,
                                                      Principal principal) {
        return editUserProfileService.editMyProfile(editProfileRequest, principal);
    }

    @GetMapping(value = "/statistics/my")
    private ResponseEntity<ResponseApi> getMyStatistic(Principal principal) {
        return editUserProfileService.getMyStatistic(principal);
    }

    @GetMapping(value = "/statistics/all")
    private ResponseEntity<ResponseApi> getAllStatistic() {
        return editUserProfileService.getAllStatistic();
    }

    @PostMapping(value = "/comment")
    private ResponseEntity<ResponseApi> postComment(@RequestBody @NotNull CommentRequest commentRequest,
                                                    Principal principal) {
        return commentService.postComment(commentRequest, principal);
    }

    @PostMapping(value = "/image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    private ResponseEntity<ResponseApi> uploadImage(@RequestParam("image") MultipartFile image) throws Exception {
        return imageService.uploadImage(image, "upload");
    }

}
