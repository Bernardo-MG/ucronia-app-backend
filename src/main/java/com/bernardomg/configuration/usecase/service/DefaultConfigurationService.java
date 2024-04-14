
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
    public final Optional<Configuration> getOne(final String key) {
        final Optional<Configuration> configuration;

        configuration = configurationRepository.findOne(key);
        if (configuration.isEmpty()) {
            throw new MissingConfigurationException(key);
        }

        return configuration;
    }

    @Override
    public final Configuration update(final String key, final Configuration configuration) {
        final Configuration toSave;
        final Optional<Configuration> existing;

        existing = configurationRepository.findOne(key);
        if (existing.isEmpty()) {
            throw new MissingConfigurationException(key);
        }

        toSave = Configuration.builder()
            .withCode(configuration.getCode())
            .withType(existing.get().getType())
            .withValue(configuration.getValue())
            .build();

        return configurationRepository.save(toSave);
    }

}
