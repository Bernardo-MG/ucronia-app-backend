
package com.bernardomg.association.configuration.test.service.unit;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.test.config.factory.AssociationConfigurations;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.test.config.factory.Configurations;
import com.bernardomg.configuration.usecase.source.ConfigurationSource;

@ExtendWith(MockitoExtension.class)
@DisplayName("Association configuration service - update")
public class TestAssociationConfigurationServiceUpdate {

    @Mock
    private ConfigurationRepository                configurationRepository;

    @Mock
    private ConfigurationSource                    configurationSource;

    @InjectMocks
    private DefaultAssociationConfigurationService service;

    @Test
    @DisplayName("When updating the configuration, the change is persisted")
    void testUpdate_PersistedData() {
        final AssociationConfiguration configuration;

        // GIVEN
        configuration = AssociationConfigurations.amount();

        // WHEN
        service.update(configuration);

        // THEN
        verify(configurationRepository).save(Configurations.amount());
    }

}
