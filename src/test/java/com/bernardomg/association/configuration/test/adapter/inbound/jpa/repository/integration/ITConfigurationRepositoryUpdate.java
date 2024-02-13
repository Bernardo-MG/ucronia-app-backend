
package com.bernardomg.association.configuration.test.adapter.inbound.jpa.repository.integration;

import java.util.Collection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.test.config.factory.ConfigurationEntities;
import com.bernardomg.association.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.test.data.annotation.FeeAmountConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - update")
public class ITConfigurationRepositoryUpdate {

    @Autowired
    private ConfigurationSpringRepository configurationSpringRepository;

    @Autowired
    private ConfigurationRepository       repository;

    @Test
    @DisplayName("When updating the fee amount and with no existing configuration, the configuration is persisted")
    void testUpdate_NoData_Persisted() {
        final Configuration                   configuration;
        final Collection<ConfigurationEntity> configurations;

        // GIVEN
        configuration = Configurations.valid();

        // WHEN
        repository.save(configuration);

        // THEN
        Assertions.assertThat(configurationSpringRepository.count())
            .isOne();

        configurations = configurationSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(ConfigurationEntities.valid());
    }

    @Test
    @DisplayName("When updating the fee amount, the configuration is persisted")
    @FeeAmountConfiguration
    void testUpdate_Persisted() {
        final Configuration                   configuration;
        final Collection<ConfigurationEntity> configurations;

        // GIVEN
        configuration = Configurations.valid();

        // WHEN
        repository.save(configuration);

        // THEN
        Assertions.assertThat(configurationSpringRepository.count())
            .isOne();

        configurations = configurationSpringRepository.findAll();

        Assertions.assertThat(configurations)
            .as("configurations")
            .containsExactly(ConfigurationEntities.valid());
    }

    @Test
    @DisplayName("When updating the fee amount, the configuration is returned")
    @FeeAmountConfiguration
    void testUpdate_Returned() {
        final Configuration configuration;
        final Configuration created;

        // GIVEN
        configuration = Configurations.valid();

        // WHEN
        created = repository.save(configuration);

        // THEN
        Assertions.assertThat(configurationSpringRepository.count())
            .isOne();

        Assertions.assertThat(created)
            .as("created")
            .isEqualTo(Configurations.valid());
    }

}
