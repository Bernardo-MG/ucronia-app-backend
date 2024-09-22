
package com.bernardomg.settings.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.test.config.factory.SettingConstants;
import com.bernardomg.settings.test.config.factory.Settings;
import com.bernardomg.settings.usecase.service.DefaultSettingService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration service - get one")
public class TestSettingUpdate {

    @InjectMocks
    private DefaultSettingService service;

    @Mock
    private SettingRepository     settingRepository;

    @Test
    @DisplayName("When the configuration exists, it is updated")
    void testUpdate_Existing() {
        // GIVEN
        given(settingRepository.findOne(SettingConstants.CODE)).willReturn(Optional.of(Settings.intValue()));

        // WHEN
        service.update(SettingConstants.CODE, SettingConstants.NUMBER_VALUE);

        // THEN
        verify(settingRepository).save(Settings.intValue());
    }

    @Test
    @DisplayName("When the configuration doesn't exist, nothing is updated")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(settingRepository.findOne(SettingConstants.CODE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.update(SettingConstants.CODE, SettingConstants.NUMBER_VALUE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingSettingException.class);
    }

}
