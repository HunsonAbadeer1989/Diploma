package main.service;

import main.api.request.SettingsRequest;
import main.api.response.ResponseApi;
import org.springframework.http.ResponseEntity;

public interface SettingService {

    String MULTIUSER_MODE = "MULTIUSER_MODE";
    String POST_PREMODERATION = "POST_PREMODERATION";
    String STATISTICS_IS_PUBLIC = "STATISTICS_IS_PUBLIC";

    ResponseEntity<ResponseApi> getGlobalSettings();

    ResponseEntity<ResponseApi> putGlobalSettings(SettingsRequest settingsRequest);

}
