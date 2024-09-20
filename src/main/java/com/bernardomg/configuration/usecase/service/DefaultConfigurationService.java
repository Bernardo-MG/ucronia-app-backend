
package com.bernardomg.configuration.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.configuration.domain.exception.MissingConfigurationException;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
public final class DefaultConfigurationService implements ConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public DefaultConfigurationService(final ConfigurationRepository configRepository) {
        super();

        configurationRepository = Objects.requireNonNull(configRepository);
    }

    @Override
    public final Collection<Configuration> getAll() {
        return configurationRepository.findAll();
    }

    @Override
    public final Optional<Configuration> getOne(final String code) {
        final Optional<Configuration> configuration;

        configuration = configurationRepository.findOne(code);
        if (configuration.isEmpty()) {
            log.error("Missing configuration {}", code);
            throw new MissingConfigurationException(code);
        }

        return configuration;
    }

    @Override
    public final Configuration update(final String code, final String value) {
        final Configuration toSave;
        final Configuration existing;

        existing = configurationRepository.findOne(code)
            .orElseThrow(() -> {
                log.error("Missing configuration {}", code);
                throw new MissingConfigurationException(code);
            });

        toSave = Configuration.builder()
            .withCode(code)
            .withType(existing.getType())
            .withValue(value)
            .build();

        return configurationRepository.save(toSave);
    }

}
