
package com.bernardomg.configuration.test.service.unit;

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

import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.usecase.service.DefaultConfigurationService;

@ExtendWith(MockitoExtension.class)
@DisplayName("Configuration service - get all")
public class TestConfigurationGetAll {

    @Mock
    private ConfigurationRepository     configurationRepository;

    @InjectMocks
    private DefaultConfigurationService service;

    @Test
    @DisplayName("When the configuration exists, it is returned")
    void testGetAll_Existing() {
        final Collection<Configuration> configurations;

        // GIVEN
        given(configurationRepository.findAll()).willReturn(List.of(Configurations.stringValue()));

        // WHEN
        configurations = service.getAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Configurations.stringValue());
    }

    @Test
    @DisplayName("When no configuration exists, nothing is returned")
    void testGetAll_NotExisting() {
        final Collection<Configuration> configurations;

        // GIVEN
        given(configurationRepository.findAll()).willReturn(List.of());

        // WHEN
        configurations = service.getAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .isEmpty();
    }

}
