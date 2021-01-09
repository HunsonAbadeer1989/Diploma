package main.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.service.SettingService;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalSettingRequest implements RequestApi{

    @JsonProperty(SettingService.MULTIUSER_MODE)
    private Boolean multiuserMode = null;
    @JsonProperty(SettingService.POST_PREMODERATION)
    private Boolean postPremoderation = null;
    @JsonProperty(SettingService.STATISTICS_IS_PUBLIC)
    private Boolean statisticsIsPublic = null;

}
