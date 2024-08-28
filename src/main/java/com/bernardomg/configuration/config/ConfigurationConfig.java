
package com.bernardomg.configuration.config;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.bernardomg.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.configuration.adapter.inbound.jpa.repository.JpaConfigurationRepository;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.configuration.usecase.service.ConfigurationService;
import com.bernardomg.configuration.usecase.service.DefaultConfigurationService;
import com.bernardomg.security.web.whitelist.WhitelistRoute;

@Configuration
@ComponentScan({ "com.bernardomg.configuration.adapter.outbound.rest.controller" })
@AutoConfigurationPackage(basePackages = { "com.bernardomg.configuration.adapter.inbound.jpa" })
public class ConfigurationConfig {

    @Bean("configurationRepository")
    public ConfigurationRepository
            configurationRepository(final ConfigurationSpringRepository configurationSpringRepo) {
        return new JpaConfigurationRepository(configurationSpringRepo);
    }

    @Bean("configurationWhitelist")
    public WhitelistRoute geActivateUserWhitelist() {
        return WhitelistRoute.of("/configuration/public/**", HttpMethod.GET);
    }

    @Bean("configurationService")
    public ConfigurationService
            getAssociationConfigurationService(final ConfigurationRepository configurationRepository) {
        return new DefaultConfigurationService(configurationRepository);
    }

}
