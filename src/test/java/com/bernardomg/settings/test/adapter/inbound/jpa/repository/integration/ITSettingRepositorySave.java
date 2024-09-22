
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.settings.adapter.inbound.jpa.model.SettingsEntity;
import com.bernardomg.settings.adapter.inbound.jpa.repository.SettingsSpringRepository;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.config.data.annotation.CleanSetting;
import com.bernardomg.settings.test.config.data.annotation.FeeAmountSetting;
import com.bernardomg.settings.test.config.factory.SettingEntities;
import com.bernardomg.settings.test.config.factory.Settings;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SettingRepository - save")
public class ITSettingRepositorySave {

    @Autowired
    private SettingRepository        repository;

    @Autowired
    private SettingsSpringRepository settingSpringRepository;

    @Test
    @DisplayName("When saving the fee amount and with no existing setting, the setting is persisted")
    @CleanSetting
    void testSave_NoData_Persisted() {
        final Setting                    setting;
        final Collection<SettingsEntity> settings;

        // GIVEN
        setting = Settings.amount();

        // WHEN
        repository.save(setting);

        // THEN
        settings = settingSpringRepository.findAll();

        Assertions.assertThat(settings)
            .as("settings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the setting is persisted")
    @CleanSetting
    @FeeAmountSetting
    void testSave_Persisted() {
        final Setting                    setting;
        final Collection<SettingsEntity> settings;

        // GIVEN
        setting = Settings.amount();

        // WHEN
        repository.save(setting);

        // THEN
        settings = settingSpringRepository.findAll();

        Assertions.assertThat(settings)
            .as("settings")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the setting is returned")
    @FeeAmountSetting
    void testSave_Returned() {
        final Setting setting;
        final Setting created;

        // GIVEN
        setting = Settings.amount();

        // WHEN
        created = repository.save(setting);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Settings.amount());
    }

}
