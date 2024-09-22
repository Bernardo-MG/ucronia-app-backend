
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.config.data.annotation.CleanSetting;
import com.bernardomg.settings.test.config.data.annotation.FloatSetting;
import com.bernardomg.settings.test.config.data.annotation.IntegerSetting;
import com.bernardomg.settings.test.config.data.annotation.MultipleSetting;
import com.bernardomg.settings.test.config.data.annotation.StringSetting;
import com.bernardomg.settings.test.config.factory.Settings;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - find one")
public class ITSettingRepositoryFindAll {

    @Autowired
    private SettingRepository repository;

    @Test
    @DisplayName("When reading a float configuration, it is returned")
    @CleanSetting
    @FloatSetting
    void testFindAll_Float() {
        final Collection<Setting> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Settings.floatValue());
    }

    @Test
    @DisplayName("When reading a integer configuration, it is returned")
    @CleanSetting
    @IntegerSetting
    void testFindAll_Integer() {
        final Collection<Setting> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Settings.intValue());
    }

    @Test
    @DisplayName("When reading a string configuration, it is returned")
    @CleanSetting
    @MultipleSetting
    void testFindAll_Multiple() {
        final Collection<Setting> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Settings.first(), Settings.second());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    @CleanSetting
    void testFindAll_NoData() {
        final Collection<Setting> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading a string configuration, it is returned")
    @CleanSetting
    @StringSetting
    void testFindAll_String() {
        final Collection<Setting> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Settings.stringValue());
    }

}