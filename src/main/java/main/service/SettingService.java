package main.service;

import main.model.GlobalSettings;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;

public interface SettingService {

    String MULTIUSER_MODE = "MULTIUSER_MODE";
    String POST_PREMODERATION = "POST_PREMODERATION";
    String STATISTICS_IS_PUBLIC = "STATISTICS_IS_PUBLIC";
    String MULTIUSER_MODE_NAME = "Многопользовательский режим";
    String POST_PREMODERATION_NAME = "Премодерация постов";
    String STATISTICS_IS_PUBLIC_NAME = "Показывать всем статистику блога";

    ResponseEntity<?> getGlobalSettings();

    public HashSet<GlobalSettings> getAllGlobalSettingsSet();
}
