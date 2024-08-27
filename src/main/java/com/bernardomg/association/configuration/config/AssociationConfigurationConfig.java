
package com.bernardomg.association.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.association.configuration.adapter.inbound.source.DefaultAssociationConfigurationSource;
import com.bernardomg.association.configuration.security.register.AssociationConfigurationPermissionRegister;
import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.security.permission.initializer.usecase.PermissionRegister;

@Configuration
public class AssociationConfigurationConfig {

    @Bean("associationConfigurationPermissionRegister")
    public PermissionRegister getAssociationConfigurationPermissionRegister() {
        return new AssociationConfigurationPermissionRegister();
    }

    @Bean("associationConfigurationSource")
    public AssociationConfigurationSource
            getAssociationConfigurationSource(final ConfigurationRepository configurationRepo) {
        return new DefaultAssociationConfigurationSource(configurationRepo);
    }

}
