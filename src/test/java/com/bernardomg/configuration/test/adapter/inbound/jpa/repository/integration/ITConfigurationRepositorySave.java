
package com.bernardomg.configuration.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.data.annotation.CleanConfiguration;
import com.bernardomg.configuration.test.config.data.annotation.FeeAmountConfiguration;
import com.bernardomg.configuration.test.config.factory.ConfigurationEntities;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - save")
public class ITConfigurationRepositorySave {

    @Autowired
    private ConfigurationSpringRepository configurationSpringRepository;

    @Autowired
    private ConfigurationRepository       repository;

    @Test
    @DisplayName("When saving the fee amount and with no existing configuration, the configuration is persisted")
    @CleanConfiguration
    void testSave_NoData_Persisted() {
        final Configuration                   configuration;
        final Collection<ConfigurationEntity> configurations;

        // GIVEN
        configuration = Configurations.amount();

        // WHEN
        repository.save(configuration);

        // THEN
        configurations = configurationSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(ConfigurationEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the configuration is persisted")
    @CleanConfiguration
    @FeeAmountConfiguration
    void testSave_Persisted() {
        final Configuration                   configuration;
        final Collection<ConfigurationEntity> configurations;

        // GIVEN
        configuration = Configurations.amount();

        // WHEN
        repository.save(configuration);

        // THEN
        configurations = configurationSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
            .containsExactly(ConfigurationEntities.amount());
    }

    @Test
    @DisplayName("When saving the fee amount, the configuration is returned")
    @FeeAmountConfiguration
    void testSave_Returned() {
        final Configuration configuration;
        final Configuration created;

        // GIVEN
        configuration = Configurations.amount();

        // WHEN
        created = repository.save(configuration);

        // THEN
        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Configurations.amount());
    }

}
