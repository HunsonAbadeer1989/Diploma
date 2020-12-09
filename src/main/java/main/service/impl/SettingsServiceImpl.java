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

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @Override
    public ResponseEntity<?> getGlobalSettings() {
        GlobalSettingsResponse responseSettings = new GlobalSettingsResponse();
        return new ResponseEntity<ResponseApi>(responseSettings, HttpStatus.OK);
    }
}
