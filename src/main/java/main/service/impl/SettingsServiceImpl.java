package main.service.impl;

import main.api.response.GlobalSettingsResponse;
import main.api.response.ResponseApi;
import main.model.GlobalSettings;
import main.repository.GlobalSettingsRepository;
import main.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class SettingsServiceImpl implements SettingService {

    @Value("${global_settings.default_values.multiuser_mode}")
    private String multiuserDefaultValue;
    @Value("${global_settings.default_values.statistics_is_public}")
    private String statisticsDefaultValue;
    @Value("${global_settings.default_values.post_premoderation}")
    private String postPremoderationDefaultValue;

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @Override
    public ResponseEntity<?> getGlobalSettings() {
        GlobalSettingsResponse responseSettings = new GlobalSettingsResponse(getAllGlobalSettingsSet());
        ResponseEntity<ResponseApi> response = new ResponseEntity<>(responseSettings, HttpStatus.OK);
        return response;
    }

    @Override
    public HashSet<GlobalSettings> getAllGlobalSettingsSet() {
        HashSet<GlobalSettings> gsSet = new HashSet<>(globalSettingsRepository.findAll());
            boolean hasMultiuserMode = false;
            boolean hasPostPremoderation = false;
            boolean hasStatisticsIsPublic = false;
            for (GlobalSettings g : gsSet) {
                String globalSettingsCode = g.getCode().toUpperCase();
                switch (globalSettingsCode) {
                    case (MULTIUSER_MODE):
                        hasMultiuserMode = true;
                        break;
                    case (POST_PREMODERATION):
                        hasPostPremoderation = true;
                        break;
                    case (STATISTICS_IS_PUBLIC):
                        hasStatisticsIsPublic = true;
                        break;
                }
                setDefaultsToMissedSettings(gsSet, hasMultiuserMode, hasPostPremoderation, hasStatisticsIsPublic);
            }
        return gsSet;
    }

    private void setDefaultsToMissedSettings(HashSet<GlobalSettings> gsSet, boolean hasMultiuserMode,
                                             boolean hasPostPremoderation, boolean hasStatisticsIsPublic) {
        if (!hasMultiuserMode) gsSet.add(addNewGlobalSetting(
                MULTIUSER_MODE, MULTIUSER_MODE_NAME, multiuserDefaultValue));
        if (!hasPostPremoderation) gsSet.add(addNewGlobalSetting(
                POST_PREMODERATION, POST_PREMODERATION_NAME, postPremoderationDefaultValue));
        if (!hasStatisticsIsPublic) gsSet.add(addNewGlobalSetting(
                STATISTICS_IS_PUBLIC, STATISTICS_IS_PUBLIC_NAME, statisticsDefaultValue));
    }

    private GlobalSettings addNewGlobalSetting(String code, String name, String value) {
        GlobalSettings g = new GlobalSettings(code, name, value);
        g = globalSettingsRepository.save(g);
        return g;
    }
}
