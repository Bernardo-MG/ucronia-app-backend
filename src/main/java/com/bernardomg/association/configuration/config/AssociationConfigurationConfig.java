
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.JpaConfigurationRepository;
import com.bernardomg.association.configuration.adapter.inbound.source.CompositeAssociationConfigurationSource;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.usecase.service.AssociationConfigurationService;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;
import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.configuration.usecase.source.ConfigurationSource;
import com.bernardomg.association.configuration.usecase.source.DefaultConfigurationSource;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("configurationRepository")
    public ConfigurationRepository
            configurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        return new JpaConfigurationRepository(configurationSpringRepo);
    }

    @Bean("configurationSource")
    public ConfigurationSource configurationSource(final ConfigurationRepository configurationRepository) {
        return new DefaultConfigurationSource(configurationRepository);
    }

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
