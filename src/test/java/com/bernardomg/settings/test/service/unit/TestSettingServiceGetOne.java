
package com.bernardomg.settings.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.settings.domain.exception.MissingSettingException;
import com.bernardomg.settings.domain.model.Setting;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.configuration.factory.SettingConstants;
import com.bernardomg.settings.test.configuration.factory.Settings;
import com.bernardomg.settings.usecase.service.DefaultSettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Settings service - get one")
public class TestSettingServiceGetOne {

    @InjectMocks
    private DefaultSettingService service;

    @Mock
    private SettingRepository     settingRepository;

    @Test
    @DisplayName("When the setting exists, it is returned")
    void testGetOne_Existing() {
        final Optional<Setting> settings;

        // GIVEN
        given(settingRepository.findOne(SettingConstants.CODE)).willReturn(Optional.of(Settings.stringValue()));

        // WHEN
        settings = service.getOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThat(settings)
            .as("settings")
            .contains(Settings.stringValue());
    }

    @Test
    @DisplayName("When the setting doesn't exist, nothing is returned")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(settingRepository.findOne(SettingConstants.CODE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingSettingException.class);
    }

}
