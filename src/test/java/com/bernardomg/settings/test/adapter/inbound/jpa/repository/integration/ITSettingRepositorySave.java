
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
@DisplayName("ConfigurationRepository - save")
public class ITSettingRepositorySave {

    @Autowired
    private SettingRepository        repository;

    @Autowired
    private SettingsSpringRepository settingSpringRepository;

    @Test
    @DisplayName("When saving the fee amount and with no existing configuration, the configuration is persisted")
    @CleanSetting
    void testSave_NoData_Persisted() {
        final Setting                    configuration;
        final Collection<SettingsEntity> configurations;

        // GIVEN
        configuration = Settings.amount();

        // WHEN
        repository.save(configuration);

        // THEN
        configurations = settingSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the configuration is persisted")
    @CleanSetting
    @FeeAmountSetting
    void testSave_Persisted() {
        final Setting                    configuration;
        final Collection<SettingsEntity> configurations;

        // GIVEN
        configuration = Settings.amount();

        // WHEN
        repository.save(configuration);

        // THEN
        configurations = settingSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(SettingEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the configuration is returned")
    @FeeAmountSetting
    void testSave_Returned() {
        final Setting configuration;
        final Setting created;

        // GIVEN
        configuration = Settings.amount();

        // WHEN
        created = repository.save(configuration);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Settings.amount());
    }

}
