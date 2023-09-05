
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.service.AssociationConfigurationService;
import com.bernardomg.association.configuration.service.DefaultAssociationConfigurationService;
import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.configuration.source.CompositeAssociationConfigurationSource;
import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;
import com.bernardomg.configuration.source.ConfigurationSource;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("associationConfigurationService")
    public AssociationConfigurationService getAssociationConfigurationService(final ConfigurationRepository configurationRepository) {
        return new DefaultAssociationConfigurationService(configurationRepository);
    }

    @Bean("associationConfigurationSource")
    public AssociationConfigurationSource
            getAssociationConfigurationSource(final ConfigurationSource configurationSource) {
        return new CompositeAssociationConfigurationSource(configurationSource);
    }

}
