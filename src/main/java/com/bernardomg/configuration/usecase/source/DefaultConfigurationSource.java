
package com.bernardomg.configuration.usecase.source;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

public final class DefaultConfigurationSource implements ConfigurationSource {

    private final ConfigurationRepository configurationRepository;

    public DefaultConfigurationSource(final ConfigurationRepository configurationRepo) {
        super();

        configurationRepository = Objects.requireNonNull(configurationRepo);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<Configuration> read;
        final String                  text;
        final Float                   value;

        read = configurationRepository.findOne(key);
        if (read.isPresent()) {
            text = read.get()
                .getValue();
            value = Float.valueOf(text);
        } else {
            value = 0f;
        }

        return value;
    }

    @Override
    public final String getString(final String key) {
        final Optional<Configuration> read;
        final String                  value;

        read = configurationRepository.findOne(key);
        if (read.isPresent()) {
            value = read.get()
                .getValue();
        } else {
            value = "";
        }

        return value;
    }

}
