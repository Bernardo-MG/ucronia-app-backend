
package com.bernardomg.settings.configuration;

import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import com.bernardomg.security.web.whitelist.WhitelistRoute;
import com.bernardomg.settings.adapter.inbound.jpa.repository.JpaSettingsRepository;
import com.bernardomg.settings.adapter.inbound.jpa.repository.SettingsSpringRepository;
import com.bernardomg.settings.domain.repository.SettingRepository;
import com.bernardomg.settings.usecase.service.DefaultSettingService;
import com.bernardomg.settings.usecase.service.SettingService;

@Configuration
@ComponentScan({ "com.bernardomg.settings.adapter.outbound.rest.controller" })
@AutoConfigurationPackage(basePackages = { "com.bernardomg.settings.adapter.inbound.jpa" })
public class SettingsConfiguration {

    @Bean("settingRepository")
    public SettingRepository getSettingRepository(final SettingsSpringRepository settingSpringRepo) {
        return new JpaSettingsRepository(settingSpringRepo);
    }

    @Bean("settingService")
    public SettingService getSettingService(final SettingRepository settingRepository) {
        return new DefaultSettingService(settingRepository);
    }

    @Bean("settingWhitelist")
    public WhitelistRoute getSettingWhitelist() {
        return WhitelistRoute.of("/settings/public/**", HttpMethod.GET);
    }

}
