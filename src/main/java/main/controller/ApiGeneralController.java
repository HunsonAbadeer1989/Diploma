package main.controller;

import main.api.request.EditProfileRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.response.InitResponse;
import main.api.response.ResponseApi;
import main.api.response.SettingsResponse;
import main.service.SettingsService;
import main.service.impl.UserProfileServiceImpl;
import main.service.PostService;
import main.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    @Autowired
    private final InitResponse initResponse;
    @Autowired
    private final SettingsService settingsService;
    @Autowired
    private final TagService tagService;
    @Autowired
    private final PostService postService;
    @Autowired
    private final UserProfileServiceImpl editUserProfileService;

    public ApiGeneralController(InitResponse initResponse,
                                SettingsService settingsService,
                                TagService tagService,
                                PostService postService,
                                UserProfileServiceImpl editUserProfileService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagService = tagService;
        this.postService = postService;
        this.editUserProfileService = editUserProfileService;
    }

    @GetMapping("/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/settings")
    private ResponseEntity<SettingsResponse> settings() {
        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping(value = ("/tag"), params = {"query"} )
    public ResponseEntity<ResponseApi> getTagList(@RequestParam String query) {
        return tagService.getTagList(query);
    }

    @GetMapping(value = ("/tag"))
    public ResponseEntity<ResponseApi> getTagList() {
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
    private ResponseEntity<ResponseApi> editMyProfile(@RequestBody EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest) {
        return editUserProfileService.editMyProfile(editProfileRequest, httpServletRequest);
    }

    @GetMapping(value = "/statistic/my")
    private ResponseEntity<ResponseApi> getMyStatistic(HttpServletRequest httpServletRequest){
        return editUserProfileService.getMyStatistic(httpServletRequest);
    }

    @GetMapping(value = "/statistic/all")
    private ResponseEntity<ResponseApi> getAllStatistic(HttpServletRequest httpServletRequest){
        return editUserProfileService.getAllStatistic(httpServletRequest);
    }


}
