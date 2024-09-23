
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
@DisplayName("Settings service - get all")
public class TestSettingServiceGetAll {

    @InjectMocks
    private DefaultSettingService service;

    @Mock
    private SettingRepository     settingRepository;

    @Test
    @DisplayName("When the setting exists, it is returned")
    void testGetAll_Existing() {
        final Collection<Setting> settings;

        // GIVEN
        given(settingRepository.findAll()).willReturn(List.of(Settings.stringValue()));

        // WHEN
        settings = service.getAll();

        // THEN
        Assertions.assertThat(settings)
            .as("settings")
            .containsExactly(Settings.stringValue());
    }

    @Test
    @DisplayName("When no setting exists, nothing is returned")
    void testGetAll_NotExisting() {
        final Collection<Setting> settings;

        // GIVEN
        given(settingRepository.findAll()).willReturn(List.of());

        // WHEN
        settings = service.getAll();

        // THEN
        Assertions.assertThat(settings)
            .as("settings")
            .isEmpty();
    }

}
