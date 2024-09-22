
package com.bernardomg.settings.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Collection;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.config.factory.Settings;
import com.bernardomg.settings.usecase.service.DefaultSettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration service - get all")
public class TestSettingGetAll {

    @InjectMocks
    private DefaultSettingService service;

    @Mock
    private SettingRepository     settingRepository;

    @Test
    @DisplayName("When the configuration exists, it is returned")
    void testGetAll_Existing() {
        final Collection<Setting> configurations;

        // GIVEN
        given(settingRepository.findAll()).willReturn(List.of(Settings.stringValue()));

        // WHEN
        configurations = service.getAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Settings.stringValue());
    }

    @Test
    @DisplayName("When no configuration exists, nothing is returned")
    void testGetAll_NotExisting() {
        final Collection<Setting> configurations;

        // GIVEN
        given(settingRepository.findAll()).willReturn(List.of());

        // WHEN
        configurations = service.getAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .isEmpty();
    }

}
