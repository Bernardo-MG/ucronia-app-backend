
package com.bernardomg.configuration.test.service.unit;

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

import com.bernardomg.configuration.domain.exception.MissingConfigurationException;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.factory.ConfigurationConstants;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.usecase.service.DefaultConfigurationService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration service - get one")
public class TestConfigurationUpdate {

    @Mock
    private ConfigurationRepository     configurationRepository;

    @InjectMocks
    private DefaultConfigurationService service;

    @Test
    @DisplayName("When the configuration exists, it is updated")
    void testUpdate_Existing() {
        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.CODE))
            .willReturn(Optional.of(Configurations.intValue()));

        // WHEN
        service.update(ConfigurationConstants.CODE, ConfigurationConstants.NUMBER_VALUE);

        // THEN
        verify(configurationRepository).save(Configurations.intValue());
    }

    @Test
    @DisplayName("When the configuration exists and is restricted, it is updated")
    void testUpdate_Existing_Restricted() {
        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.CODE))
            .willReturn(Optional.of(Configurations.restricted()));

        // WHEN
        service.update(ConfigurationConstants.CODE, ConfigurationConstants.NUMBER_VALUE);

        // THEN
        verify(configurationRepository).save(Configurations.restricted());
    }

    @Test
    @DisplayName("When the configuration doesn't exist, nothing is updated")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.CODE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.update(ConfigurationConstants.CODE, ConfigurationConstants.NUMBER_VALUE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingConfigurationException.class);
    }

}
