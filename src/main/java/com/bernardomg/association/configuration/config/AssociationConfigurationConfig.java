
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.JpaConfigurationRepository;
import com.bernardomg.association.configuration.adapter.inbound.source.CompositeAssociationConfigurationSource;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationSource;
import com.bernardomg.association.configuration.usecase.ConfigurationSource;
import com.bernardomg.association.configuration.usecase.PersistentConfigurationSource;
import com.bernardomg.association.configuration.usecase.service.AssociationConfigurationService;
import com.bernardomg.association.configuration.usecase.service.DefaultAssociationConfigurationService;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("configurationRepository")
    public ConfigurationRepository
            configurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        return new JpaConfigurationRepository(configurationSpringRepo);
    }

    @Bean("configurationSource")
    public ConfigurationSource configurationSource(final ConfigurationSpringRepository configurationRepository) {
        return new PersistentConfigurationSource(configurationRepository);
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
