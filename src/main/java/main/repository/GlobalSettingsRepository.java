package main.repository;

import main.model.GlobalSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings, Integer> {

    @Query(value = "FROM global_settings AS g WHERE g.name LIKE :name", nativeQuery = true)
    GlobalSettings findGlobalSettingByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE global_settings AS g SET g.value = :value WHERE g.code = :code", nativeQuery = true)
    void updateGlobalSetting(@Param("code") String code,
                             @Param("value") String value);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE global_settings s SET s.value = :value WHERE s.code = :code",
            nativeQuery = true)
    void putSettings(@Param("code") String code, @Param("value") String value);

}