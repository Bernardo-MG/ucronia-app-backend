
package com.bernardomg.configuration.test.service.unit;

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

import com.bernardomg.configuration.domain.exception.MissingConfigurationException;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.factory.ConfigurationConstants;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.usecase.service.DefaultConfigurationService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration service - get one")
public class TestConfigurationGetOne {

    @Mock
    private ConfigurationRepository     configurationRepository;

    @InjectMocks
    private DefaultConfigurationService service;

    @Test
    @DisplayName("When the configuration exists, it is returned")
    void testGetOne_Existing() {
        final Optional<Configuration> configuration;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.CODE))
            .willReturn(Optional.of(Configurations.stringValue()));

        // WHEN
        configuration = service.getOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.stringValue());
    }

    @Test
    @DisplayName("When the configuration doesn't exist, nothing is returned")
    void testGetOne_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.CODE)).willReturn(Optional.empty());

        // WHEN
        execution = () -> service.getOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingConfigurationException.class);
    }

}
