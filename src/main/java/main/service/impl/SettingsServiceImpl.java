package main.service.impl;

import main.api.request.SettingsRequest;
import main.api.response.GlobalSettingsResponse;
import main.api.response.ResponseApi;
import main.model.GlobalSettings;
import main.repository.GlobalSettingsRepository;
import main.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingsServiceImpl implements SettingService {

    private final GlobalSettingsRepository globalSettingsRepository;

    public SettingsServiceImpl(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
    }

    @Override
    public ResponseEntity<ResponseApi> getGlobalSettings() {
        List<GlobalSettings> globalSettingsList = globalSettingsRepository.findAll();
        GlobalSettingsResponse responseSettings = new GlobalSettingsResponse()
                                                            .settingsToSettingsResponse(globalSettingsList);
        return new ResponseEntity<ResponseApi>(responseSettings, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseApi> putGlobalSettings(SettingsRequest settingsRequest) {
        String MULTIUSER_MODE = settingsRequest.isMultiUserMode() ? "YES" : "NO";
        String POST_PREMODERATION = settingsRequest.isPostPreModeration() ? "YES" : "NO";
        String STATISTICS_IS_PUBLIC = settingsRequest.isStatisticsIsPublic() ? "YES" : "NO";

        globalSettingsRepository.putSettings("MULTIUSER_MODE", MULTIUSER_MODE);
        globalSettingsRepository.putSettings("POST_PREMODERATION", POST_PREMODERATION);
        globalSettingsRepository.putSettings("STATISTICS_IS_PUBLIC", STATISTICS_IS_PUBLIC);

        return new ResponseEntity(true, HttpStatus.OK);
    }
}
