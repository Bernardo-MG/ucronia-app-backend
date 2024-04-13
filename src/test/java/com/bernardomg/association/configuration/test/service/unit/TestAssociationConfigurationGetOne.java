
package com.bernardomg.association.configuration.test.service.unit;

import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.test.config.factory.ConfigurationConstants;
import com.bernardomg.association.configuration.test.config.factory.Configurations;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;
import com.bernardomg.association.configuration.usecase.source.ConfigurationSource;

@ExtendWith(MockitoExtension.class)
@DisplayName("Association configuration service - get one")
public class TestAssociationConfigurationGetOne {

    @Mock
    private ConfigurationRepository                configurationRepository;

    @Mock
    private ConfigurationSource                    configurationSource;

    @InjectMocks
    private DefaultAssociationConfigurationService service;

    @Test
    @DisplayName("When the configuration exists, it is returned")
    void testGetOne_Existing() {
        final Optional<Configuration> configuration;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY))
            .willReturn(Optional.of(Configurations.stringValue()));

        // WHEN
        configuration = service.getOne(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.stringValue());
    }

    @Test
    @DisplayName("When the configuration doesn't exist, nothing is returned")
    void testGetOne_NotExisting() {
        final Optional<Configuration> configuration;

        // GIVEN
        given(configurationRepository.findOne(ConfigurationConstants.KEY)).willReturn(Optional.empty());

        // WHEN
        configuration = service.getOne(ConfigurationConstants.KEY);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .isEmpty();
    }

}
