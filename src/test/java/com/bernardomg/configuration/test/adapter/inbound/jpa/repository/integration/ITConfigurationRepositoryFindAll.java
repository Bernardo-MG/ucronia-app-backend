
package com.bernardomg.configuration.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.test.data.annotation.CleanConfiguration;
import com.bernardomg.configuration.test.data.annotation.FloatConfiguration;
import com.bernardomg.configuration.test.data.annotation.IntegerConfiguration;
import com.bernardomg.configuration.test.data.annotation.MultipleConfiguration;
import com.bernardomg.configuration.test.data.annotation.StringConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - find one")
public class ITConfigurationRepositoryFindAll {

    @Autowired
    private ConfigurationRepository repository;

    @Test
    @DisplayName("When reading a float configuration, it is returned")
    @CleanConfiguration
    @FloatConfiguration
    void testFindAll_Float() {
        final Collection<Configuration> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Configurations.floatValue());
    }

    @Test
    @DisplayName("When reading a integer configuration, it is returned")
    @CleanConfiguration
    @IntegerConfiguration
    void testFindAll_Integer() {
        final Collection<Configuration> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Configurations.intValue());
    }

    @Test
    @DisplayName("When reading a string configuration, it is returned")
    @CleanConfiguration
    @MultipleConfiguration
    void testFindAll_Multiple() {
        final Collection<Configuration> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Configurations.first(), Configurations.second());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    @CleanConfiguration
    void testFindAll_NoData() {
        final Collection<Configuration> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .isEmpty();
    }

    @Test
    @DisplayName("When reading a string configuration, it is returned")
    @CleanConfiguration
    @StringConfiguration
    void testFindAll_String() {
        final Collection<Configuration> configurations;

        // WHEN
        configurations = repository.findAll();

        // THEN
        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(Configurations.stringValue());
    }

}
