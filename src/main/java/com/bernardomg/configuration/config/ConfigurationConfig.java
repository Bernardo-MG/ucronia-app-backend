
package com.bernardomg.configuration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bernardomg.configuration.source.ConfigurationSource;
import com.bernardomg.configuration.source.PersistentConfigurationSource;

@Configuration
public class ConfigurationConfig {

    @Bean("ConfigurationSource")
    public ConfigurationSource configurationSource() {
        return new PersistentConfigurationSource();
    }

}
