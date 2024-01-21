
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.adapter.inbound.source.AssociationConfigurationSource;
import com.bernardomg.association.configuration.adapter.inbound.source.CompositeAssociationConfigurationSource;
import com.bernardomg.association.configuration.usecase.service.AssociationConfigurationService;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;
import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;
import com.bernardomg.configuration.source.ConfigurationSource;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("associationConfigurationService")
    public AssociationConfigurationService getAssociationConfigurationService(
            final ConfigurationSource configurationSource, final ConfigurationRepository configurationRepository) {
        return new DefaultAssociationConfigurationService(configurationSource, configurationRepository);
    }

    @Bean("associationConfigurationSource")
    public AssociationConfigurationSource
            getAssociationConfigurationSource(final ConfigurationSource configurationSource) {
        return new CompositeAssociationConfigurationSource(configurationSource);
    }

}
