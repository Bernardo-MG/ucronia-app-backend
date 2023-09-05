
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.source.AssociationConfigurationSource;
import com.bernardomg.association.configuration.source.CompositeAssociationConfigurationSource;
import com.bernardomg.configuration.source.ConfigurationSource;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("associationConfigurationSource")
    public AssociationConfigurationSource
            associationConfigurationSource(final ConfigurationSource configurationSource) {
        return new CompositeAssociationConfigurationSource(configurationSource);
    }

}
