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

    @Autowired
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
        return null;
    }
}
