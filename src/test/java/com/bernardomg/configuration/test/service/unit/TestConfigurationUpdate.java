
package com.bernardomg.configuration.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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
    @DisplayName("When the configuration exists, it is returned")
    void testUpdate_Existing() {
        // GIVEN
        given(configurationRepository.exists(ConfigurationConstants.KEY)).willReturn(true);

        // WHEN
        service.update(ConfigurationConstants.KEY, Configurations.intValue());

        // THEN
        verify(configurationRepository).save(Configurations.intValue());
    }

    @Test
    @DisplayName("When the configuration doesn't exist, nothing is returned")
    void testUpdate_NotExisting() {
        final ThrowingCallable execution;

        // GIVEN
        given(configurationRepository.exists(ConfigurationConstants.KEY)).willReturn(false);

        // WHEN
        execution = () -> service.update(ConfigurationConstants.KEY, Configurations.intValue());

        // THEN
        Assertions.assertThatThrownBy(execution)
            .isInstanceOf(MissingConfigurationException.class);
    }

}
