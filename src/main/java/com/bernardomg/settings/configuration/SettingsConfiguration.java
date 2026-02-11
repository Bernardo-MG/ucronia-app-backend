/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2022-2025 Bernardo Martínez Garrido
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        // TODO: this is for the public settings
        return WhitelistRoute.of("/settings/public/**", HttpMethod.GET);
    }

}
