
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.configuration.data.annotation.CleanSetting;
import com.bernardomg.settings.test.configuration.data.annotation.FloatSetting;
import com.bernardomg.settings.test.configuration.data.annotation.IntegerSetting;
import com.bernardomg.settings.test.configuration.data.annotation.MultipleSetting;
import com.bernardomg.settings.test.configuration.data.annotation.StringSetting;
import com.bernardomg.settings.test.configuration.factory.Settings;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SettingRepository - find all")
public class ITSettingRepositoryFindAll {

    @Autowired
    private SettingRepository repository;

    @Test
    @DisplayName("When reading a float setting, it is returned")
    @CleanSetting
    @FloatSetting
    void testFindAll_Float() {
        final Collection<Setting> settingsSource;

        // WHEN
        settingsSource = repository.findAll();

        // THEN
        Assertions.assertThat(settingsSource)
            .as("settingsSource")
            .containsExactly(Settings.floatValue());
    }

    @Test
    @DisplayName("When reading a integer setting, it is returned")
    @CleanSetting
    @IntegerSetting
    void testFindAll_Integer() {
        final Collection<Setting> settingsSource;

        // WHEN
        settingsSource = repository.findAll();

        // THEN
        Assertions.assertThat(settingsSource)
            .as("settingsSource")
            .containsExactly(Settings.intValue());
    }

    @Test
    @DisplayName("When reading a string setting, it is returned")
    @CleanSetting
    @MultipleSetting
    void testFindAll_Multiple() {
        final Collection<Setting> settingsSource;

        // WHEN
        settingsSource = repository.findAll();

        // THEN
        Assertions.assertThat(settingsSource)
            .as("settingsSource")
            .containsExactly(Settings.first(), Settings.second());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    @CleanSetting
    void testFindAll_NoData() {
        final Collection<Setting> settingsSource;

        // WHEN
        settingsSource = repository.findAll();

        // THEN
        Assertions.assertThat(settingsSource)
            .as("settingsSource")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading a string setting, it is returned")
    @CleanSetting
    @StringSetting
    void testFindAll_String() {
        final Collection<Setting> settingsSource;

        // WHEN
        settingsSource = repository.findAll();

        // THEN
        Assertions.assertThat(settingsSource)
            .as("settingsSource")
            .containsExactly(Settings.stringValue());
    }

}
