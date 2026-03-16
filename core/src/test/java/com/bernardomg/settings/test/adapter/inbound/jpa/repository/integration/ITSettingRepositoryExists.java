
package com.bernardomg.settings.test.adapter.inbound.jpa.repository.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.configuration.data.annotation.FloatSetting;
import com.bernardomg.settings.test.configuration.factory.SettingConstants;
import com.bernardomg.test.configuration.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("SettingRepository - exists")
public class ITSettingRepositoryExists {

    @Autowired
    private SettingRepository repository;

    @Test
    @DisplayName("When the setting exists, it is indicated as so")
    @FloatSetting
    void testExists_Existing() {
        final boolean exists;

        // WHEN
        exists = repository.exists(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(exists)
            .as("existing")
            .isTrue();
    }

    @Test
    @DisplayName("When the setting doesn't exist, it is indicated as so")
    void testExists_NotExisting() {
        final boolean exists;

        // WHEN
        exists = repository.exists(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(exists)
            .as("existing")
            .isFalse();
    }

}
