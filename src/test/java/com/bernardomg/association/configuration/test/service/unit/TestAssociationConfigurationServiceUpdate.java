
package com.bernardomg.association.configuration.test.service.unit;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.test.config.factory.AssociationConfigurations;
import com.bernardomg.association.configuration.test.config.factory.Configurations;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.ConfigurationSource;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;

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
        given(configurationRepository.findOne(AssociationConfigurationKey.FEE_AMOUNT))
            .willReturn(Optional.of(Configurations.amount()));

        configuration = AssociationConfigurations.amount();

        // WHEN
        service.update(configuration);

        // THEN
        verify(configurationRepository).save(Configurations.amount());
    }

}
