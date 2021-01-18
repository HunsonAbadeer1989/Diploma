package main.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import main.model.GlobalSettings;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSettingsResponse implements ResponseApi{

    @JsonProperty("MULTIUSER_MODE")
    private boolean multiUserMode;
    @JsonProperty("POST_PREMODERATION")
    private boolean postPreModeration;
    @JsonProperty("STATISTICS_IS_PUBLIC")
    private boolean statisticsIsPublic;

    public GlobalSettingsResponse settingsToSettingsResponse(List<GlobalSettings> settings) {
        if (settings == null) {
            return null;
        }

        GlobalSettingsResponse settingsResponse = new GlobalSettingsResponse();
        for (GlobalSettings setting : settings) {
            String settingName = setting.getCode();
            boolean value = setting.getValue().equalsIgnoreCase("YES");
            switch (settingName) {
                case "MULTIUSER_MODE":
                    settingsResponse.setMultiUserMode(value);
                    break;
                case "POST_PREMODERATION":
                    settingsResponse.setPostPreModeration(value);
                    break;
                case "STATISTICS_IS_PUBLIC":
                    settingsResponse.setStatisticsIsPublic(value);
                    break;
            }
        }
        return settingsResponse;
    }
}