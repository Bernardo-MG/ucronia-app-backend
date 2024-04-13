
package com.bernardomg.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.configuration.adapter.inbound.jpa.repository.JpaConfigurationRepository;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.usecase.service.ConfigurationService;
import com.bernardomg.configuration.usecase.service.DefaultConfigurationService;
import com.bernardomg.configuration.usecase.source.ConfigurationSource;
import com.bernardomg.configuration.usecase.source.DefaultConfigurationSource;

@Configuration
public class ConfigurationConfig {

    @Bean("configurationRepository")
    public ConfigurationRepository
            configurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        return new JpaConfigurationRepository(configurationSpringRepo);
    }

    @Bean("configurationSource")
    public ConfigurationSource configurationSource(final ConfigurationRepository configurationRepository) {
        return new DefaultConfigurationSource(configurationRepository);
    }

    @Bean("configurationService")
    public ConfigurationService
            getAssociationConfigurationService(final ConfigurationRepository configurationRepository) {
        return new DefaultConfigurationService(configurationRepository);
    }

}
