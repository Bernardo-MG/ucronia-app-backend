
package com.bernardomg.configuration.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.data.annotation.FloatConfiguration;
import com.bernardomg.configuration.test.config.data.annotation.IntegerConfiguration;
import com.bernardomg.configuration.test.config.data.annotation.StringConfiguration;
import com.bernardomg.configuration.test.config.factory.ConfigurationConstants;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - find one")
public class ITConfigurationRepositoryFindOne {

    @Autowired
    private ConfigurationRepository repository;

    @Test
    @DisplayName("When reading a float configuration, it is returned")
    @FloatConfiguration
    void testFindOne_Float() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.floatValue());
    }

    @Test
    @DisplayName("When reading a integer configuration, it is returned")
    @IntegerConfiguration
    void testFindOne_Integer() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.intValue());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    void testFindOne_NoData() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading a string configuration, it is returned")
    @StringConfiguration
    void testFindOne_String() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(ConfigurationConstants.CODE);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.stringValue());
    }

}
