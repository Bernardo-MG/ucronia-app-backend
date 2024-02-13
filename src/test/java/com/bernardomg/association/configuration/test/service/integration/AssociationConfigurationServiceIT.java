
package com.bernardomg.association.configuration.test.service.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.test.config.factory.AssociationConfigurations;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.service.AssociationConfigurationService;
import com.bernardomg.configuration.test.data.annotation.FeeAmountConfiguration;
import com.bernardomg.test.config.annotation.IntegrationTest;

@IntegrationTest
@DisplayName("Association configuration service")
public class AssociationConfigurationServiceIT {

    @Autowired
    private ConfigurationSpringRepository         repository;

    @Autowired
    private AssociationConfigurationService service;

    @Test
    @DisplayName("When updating the fee amount, the configuration is persisted")
    @FeeAmountConfiguration
    void testUpdate_FeeAmount() {
        final AssociationConfiguration configurationRequest;
        final ConfigurationEntity  configuration;

        // GIVEN
        configurationRequest = AssociationConfigurations.amount();

        // WHEN
        service.update(configurationRequest);

        // THEN
        Assertions.assertThat(repository.count())
            .isOne();

        configuration = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(configuration.getKey())
            .isEqualTo(AssociationConfigurationKey.FEE_AMOUNT);
        Assertions.assertThat(configuration.getValue())
            .isEqualTo("2.0");
    }

    @Test
    @DisplayName("When updating the fee amount and with no existing configuration, the configuration is persisted")
    void testUpdate_NoData_FeeAmount() {
        final AssociationConfiguration configurationRequest;
        final ConfigurationEntity  configuration;

        // GIVEN
        configurationRequest = AssociationConfigurations.amount();

        // WHEN
        service.update(configurationRequest);

        Assertions.assertThat(repository.count())
            .isOne();

        configuration = repository.findAll()
            .iterator()
            .next();

        Assertions.assertThat(configuration.getKey())
            .isEqualTo(AssociationConfigurationKey.FEE_AMOUNT);
        Assertions.assertThat(configuration.getValue())
            .isEqualTo("2.0");
    }

}
