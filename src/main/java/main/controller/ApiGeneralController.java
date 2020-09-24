package main.controller;

import main.api.request.EditProfileRequest;
import main.api.request.ModerationOfPostRequest;
import main.api.request.TagRequest;
import main.api.response.InitResponse;
import main.api.response.ResponseApi;
import main.api.response.SettingsResponse;
import main.service.SettingsService;
import main.service.UserProfileRepoServiceImpl;
import main.service.interfaces.PostRepoService;
import main.service.interfaces.TagRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ApiGeneralController {

    @Autowired
    private final InitResponse initResponse;
    @Autowired
    private final SettingsService settingsService;
    @Autowired
    private final TagRepoService tagRepoService;
    @Autowired
    private final PostRepoService postRepoService;
    @Autowired
    private final UserProfileRepoServiceImpl editUserProfileService;

    public ApiGeneralController(InitResponse initResponse, SettingsService settingsService, TagRequest tagRequest, TagRepoService tagRepoService, PostRepoService postRepoService, UserProfileRepoServiceImpl editUserProfileService) {
        this.initResponse = initResponse;
        this.settingsService = settingsService;
        this.tagRepoService = tagRepoService;
        this.postRepoService = postRepoService;
        this.editUserProfileService = editUserProfileService;
    }

    @GetMapping("/api/init")
    private InitResponse init() {
        return initResponse;
    }

    @GetMapping("/api/settings")
    private ResponseEntity<SettingsResponse> settings() {
        return new ResponseEntity<>(settingsService.getGlobalSettings(), HttpStatus.OK);
    }

    @GetMapping("/api/tag")
    public ResponseEntity<ResponseApi> getTagList(@RequestParam String query) {
//        return ResponseEntity<>(tagResponse.getTags(), HttpStatus.OK);
        return tagRepoService.getTagList();
    }

    @PostMapping(value = ("/api/moderation"))
    private ResponseEntity<ResponseApi> moderationOfPost(@RequestBody ModerationOfPostRequest moderationOfPostRequest, HttpServletRequest httpServletRequest) {
        return postRepoService.moderationOfPost(moderationOfPostRequest, httpServletRequest);
    }

    @GetMapping(value = ("/api/calendar"), params = {"year"})
    private ResponseEntity<ResponseApi> calendarOfPosts(@RequestParam(value = "year") Integer year) {
        return postRepoService.calendarOfPosts(year);
    }

    @PostMapping(value = ("/api/profile/my"))
    private ResponseEntity<ResponseApi> editMyProfile(@RequestBody EditProfileRequest editProfileRequest, HttpServletRequest httpServletRequest) {
        return editUserProfileService.editMyProfile(editProfileRequest, httpServletRequest);
    }

    @GetMapping(value = "/api/statistic/my")
    private ResponseEntity<ResponseApi> getMyStatistic(HttpServletRequest httpServletRequest){
        return editUserProfileService.getMyStatistic(httpServletRequest);
    }

    @GetMapping(value = "/api/statistic/all")
    private ResponseEntity<ResponseApi> getAllStatistic(HttpServletRequest httpServletRequest){
        return editUserProfileService.getAllStatistic(httpServletRequest);
    }


}
