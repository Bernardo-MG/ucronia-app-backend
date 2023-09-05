
package com.bernardomg.configuration.source;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.configuration.persistence.model.PersistentConfiguration;
import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;

public final class PersistentConfigurationSource implements ConfigurationSource {

    private final ConfigurationRepository configurationRepository;

    public PersistentConfigurationSource(final ConfigurationRepository configurationRepo) {
        super();

        configurationRepository = Objects.requireNonNull(configurationRepo);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<PersistentConfiguration> read;
        final String                            text;
        final Float                             value;

        read = configurationRepository.findOneByKey(key);
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
        final Optional<PersistentConfiguration> read;
        final String                            value;

        read = configurationRepository.findOneByKey(key);
        if (read.isPresent()) {
            value = read.get()
                .getValue();
        } else {
            value = "";
        }

        return value;
    }

}
