package main.service;

import main.model.GlobalSettings;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

public interface SettingService {

    String MULTIUSER_MODE = "MULTIUSER_MODE";
    String POST_PREMODERATION = "POST_PREMODERATION";
    String STATISTICS_IS_PUBLIC = "STATISTICS_IS_PUBLIC";

    ResponseEntity<?> getGlobalSettings();
}
