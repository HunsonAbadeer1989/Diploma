package main.repository;

import main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {
    @Query(value = "FROM GlobalSetting AS g WHERE g.name LIKE ?1", nativeQuery = true)
    GlobalSettings findGlobalSettingByName(String name);
}