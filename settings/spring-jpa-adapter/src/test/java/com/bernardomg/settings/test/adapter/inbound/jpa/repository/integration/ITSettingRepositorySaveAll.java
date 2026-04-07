
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bernardomg.settings.TestApplication;
import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;
import com.bernardomg.settings.adapter.inbound.jpa.repository.SettingsSpringRepository;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.configuration.data.annotation.CleanSetting;
import com.bernardomg.settings.test.configuration.data.annotation.IntegerSetting;
import com.bernardomg.settings.test.factory.SettingEntities;
import com.bernardomg.settings.test.factory.Settings;
import com.bernardomg.test.annotation.IntegrationTest;

@IntegrationTest
@SpringBootTest(classes = TestApplication.class)
@DisplayName("SettingRepository - save all")
public class ITSettingRepositorySaveAll {

    @Autowired
    private SettingRepository        repository;

    @Autowired
    private SettingsSpringRepository settingSpringRepository;

    @Test
    @DisplayName("When updating the fee amount, the setting is persisted")
    @CleanSetting
    @IntegerSetting
    void testSaveAll_ChangeAmount_Persisted() {
        final Setting                    setting;
        final Collection<SettingsEntity> settings;

        // GIVEN
        setting = Settings.intValue();

        // WHEN
        repository.saveAll(List.of(setting));

        // THEN
        settings = settingSpringRepository.findAll();

        Assertions.assertThat(settings)
            .as("settings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.intValue());
    }

    @Test
    @DisplayName("When updating the fee amount, the setting is returned")
    @IntegerSetting
    void testSaveAll_ChangeAmount_Returned() {
        final Setting             setting;
        final Collection<Setting> created;

        // GIVEN
        setting = Settings.intValue();

        // WHEN
        created = repository.saveAll(List.of(setting));

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .containsExactly(Settings.intValue());
    }

    @Test
    @DisplayName("When no data is received, nothing is saved")
    @IntegerSetting
    void testSaveAll_Empty() {
        final Collection<Setting> created;

        // WHEN
        created = repository.saveAll(List.of());

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEmpty();
    }

    @Test
    @DisplayName("When saving the fee amount and with no existing setting, the setting is persisted")
    @CleanSetting
    void testSaveAll_Persisted() {
        final Setting                    setting;
        final Collection<SettingsEntity> settings;

        // GIVEN
        setting = Settings.intValue();

        // WHEN
        repository.saveAll(List.of(setting));

        // THEN
        settings = settingSpringRepository.findAll();

        Assertions.assertThat(settings)
            .as("settings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.intValue());
    }

}
