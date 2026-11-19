
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
import com.bernardomg.settings.test.factory.SettingConstants;
import com.bernardomg.settings.test.factory.Settings;
import com.bernardomg.settings.usecase.service.DefaultSettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("DefaultSettingService - getOne")
class TestSettingServiceGetOne {

    @InjectMocks
    private DefaultSettingService service;

    @Mock
    private SettingRepository     settingRepository;

    @Test
    @DisplayName("When the setting exists, then it is returned")
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
    @DisplayName("When the setting doesn't exist, then nothing is returned")
    void testGetOne_NotExisting() {
        final ThrowingCallable action;

        // GIVEN
        given(settingRepository.findOne(SettingConstants.CODE)).willReturn(Optional.empty());

        // WHEN
        action = () -> service.getOne(SettingConstants.CODE);

        // THEN
        Assertions.assertThatThrownBy(action)
            .as("When the setting doesn't exist, then nothing is returned")
            .isInstanceOf(MissingSettingException.class);
    }

}
