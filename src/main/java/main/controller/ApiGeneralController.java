package main.controller;

import main.api.request.EditProfileRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.response.InitResponse;
import main.api.response.ResponseApi;
import main.service.PostService;
import main.service.TagService;
import main.service.impl.SettingsServiceImpl;
import main.service.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    public ApiGeneralController(InitResponse initResponse,
                                SettingsServiceImpl settingsService,
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
    private ResponseEntity<?> settings() {
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
