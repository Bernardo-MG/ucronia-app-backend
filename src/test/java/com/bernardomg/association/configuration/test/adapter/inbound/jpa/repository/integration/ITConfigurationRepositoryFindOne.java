
package com.bernardomg.association.configuration.test.adapter.inbound.jpa.repository.integration;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.test.config.factory.Configurations;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.test.data.annotation.FeeAmountConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("ConfigurationRepository - find one")
public class ITConfigurationRepositoryFindOne {

    @Autowired
    private ConfigurationRepository repository;

    @Test
    @DisplayName("When reading with a configuration, it is returned")
    @FeeAmountConfiguration
    void testRead() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(AssociationConfigurationKey.FEE_AMOUNT);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .contains(Configurations.amount());
    }

    @Test
    @DisplayName("When reading with no data, nothing is returned")
    void testRead_NoData() {
        final Optional<Configuration> configuration;

        // WHEN
        configuration = repository.findOne(AssociationConfigurationKey.FEE_AMOUNT);

        // THEN
        Assertions.assertThat(configuration)
            .as("configuration")
            .isEmpty();
    }

}
