package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import main.model.GlobalSettings;
import main.service.SettingService;

import java.util.Set;

@Data
public class GlobalSettingsResponse implements ResponseApi{

    @JsonProperty("MULTIUSER_MODE")
    private boolean multiuserMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPremoderation;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsIsPublic;

    public GlobalSettingsResponse(Set<GlobalSettings> settings) {
        for (GlobalSettings g : settings) {
            String settingName = g.getCode().toUpperCase();
            switch (settingName) {
                case SettingService.MULTIUSER_MODE: {
                    multiuserMode = yesOrNoToBoolean(g.getValue());
                    break;
                }
                case SettingService.POST_PREMODERATION: {
                    postPremoderation = yesOrNoToBoolean(g.getValue());
                    break;
                }
                case SettingService.STATISTICS_IS_PUBLIC: {
                    statisticsIsPublic = yesOrNoToBoolean(g.getValue());
                    break;
                }
            }
        }
    }

    private Boolean yesOrNoToBoolean(String yesOrNo) {
        if (yesOrNo.toUpperCase().equals("YES")) {
            return true;
        } else if (yesOrNo.toUpperCase().equals("NO")) {
            return false;
        } else return null;
    }

}
