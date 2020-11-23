package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.service.SettingService;

@Data
public class GlobalSettingRequest implements RequestApi{

    @JsonProperty(SettingService.MULTIUSER_MODE)
    private Boolean multiuserMode = null;
    @JsonProperty(SettingService.POST_PREMODERATION)
    private Boolean postPremoderation = null;
    @JsonProperty(SettingService.STATISTICS_IS_PUBLIC)
    private Boolean statisticsIsPublic = null;

    public GlobalSettingRequest() {
    }

    public GlobalSettingRequest(Boolean multiuserMode, Boolean postPremoderation, Boolean statisticsIsPublic) {
        this.multiuserMode = multiuserMode;
        this.postPremoderation = postPremoderation;
        this.statisticsIsPublic = statisticsIsPublic;
    }

}
