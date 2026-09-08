/** The MIT License (MIT). Copyright (c) 2022-2025 Bernardo Martínez Garrido. */

package com.bernardomg.image.test.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;

import com.bernardomg.image.adapter.inbound.jpa.repository.ImageSpringRepository;
import com.bernardomg.image.adapter.inbound.jpa.repository.JpaImageRepository;
import com.bernardomg.image.domain.repository.ImageRepository;

@Configuration
@EnableJpaRepositories(
        basePackages = { "com.bernardomg.image.adapter.inbound.jpa", "com.bernardomg.security.adapter.inbound.jpa" })
@EntityScan(
        basePackages = { "com.bernardomg.image.adapter.inbound.jpa", "com.bernardomg.security.adapter.inbound.jpa" })
public class TestConfiguration {

    @Bean("authenticationTrustResolver")
    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return new AuthenticationTrustResolverImpl();
    }

    @Bean("imageRepository")
    public ImageRepository getImageRepository(final ImageSpringRepository repository) {
        return new JpaImageRepository(repository);
    }
}
