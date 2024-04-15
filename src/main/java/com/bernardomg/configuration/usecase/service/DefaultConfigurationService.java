
package com.bernardomg.configuration.usecase.service;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.configuration.domain.exception.MissingConfigurationException;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

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
            throw new MissingConfigurationException(code);
        }

        return configuration;
    }

    @Override
    public final Optional<Configuration> getOnePublic(final String code) {
        final Optional<Configuration> configuration;

        configuration = configurationRepository.findOnePublic(code);
        if (configuration.isEmpty()) {
            throw new MissingConfigurationException(code);
        }

        return configuration;
    }

    @Override
    public final Configuration update(final String code, final String value) {
        final Configuration           toSave;
        final Optional<Configuration> existing;

        existing = configurationRepository.findOne(code);
        if (existing.isEmpty()) {
            throw new MissingConfigurationException(code);
        }

        toSave = Configuration.builder()
            .withCode(code)
            .withType(existing.get()
                .getType())
            .withValue(value)
            .withRestricted(existing.get()
                .isRestricted())
            .build();

        return configurationRepository.save(toSave);
    }

}
