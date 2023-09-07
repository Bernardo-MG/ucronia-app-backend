
package com.bernardomg.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;
import com.bernardomg.configuration.source.ConfigurationSource;
import com.bernardomg.configuration.source.PersistentConfigurationSource;

@Configuration
public class ConfigurationConfig {

    @Bean("configurationSource")
    public ConfigurationSource configurationSource(final ConfigurationRepository configurationRepository) {
        return new PersistentConfigurationSource(configurationRepository);
    }

}
