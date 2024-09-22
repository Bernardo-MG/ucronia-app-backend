
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.config.data.annotation.FloatSetting;
import com.bernardomg.settings.test.config.data.annotation.IntegerSetting;
import com.bernardomg.settings.test.config.data.annotation.StringSetting;
import com.bernardomg.settings.test.config.factory.SettingConstants;
import com.bernardomg.settings.test.config.factory.Settings;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SettingRepository - find one")
public class ITSettingRepositoryFindOne {

    @Autowired
    private SettingRepository repository;

    @Test
    @DisplayName("When reading a float setting, it is returned")
    @FloatSetting
    void testFindOne_Float() {
        final Optional<Setting> setting;

        // WHEN
        setting = repository.findOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(setting)
            .as("settings")
            .contains(Settings.floatValue());
    }

    @Test
    @DisplayName("When reading a integer setting, it is returned")
    @IntegerSetting
    void testFindOne_Integer() {
        final Optional<Setting> setting;

        // WHEN
        setting = repository.findOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(setting)
            .as("settings")
            .contains(Settings.intValue());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Setting> setting;

        // WHEN
        setting = repository.findOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(setting)
            .as("settings")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading a string setting, it is returned")
    @StringSetting
    void testFindOne_String() {
        final Optional<Setting> setting;

        // WHEN
        setting = repository.findOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(setting)
            .as("settings")
            .contains(Settings.stringValue());
    }

}
