
package com.bernardomg.association.configuration.usecase.source;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;

public final class PersistentConfigurationSource implements ConfigurationSource {

    private final ConfigurationSpringRepository configurationRepository;

    public PersistentConfigurationSource(final ConfigurationSpringRepository configurationRepo) {
        super();

        configurationRepository = Objects.requireNonNull(configurationRepo);
    }

    @Override
    public final Float getFloat(final String key) {
        final Optional<ConfigurationEntity> read;
        final String                        text;
        final Float                         value;

        read = configurationRepository.findByKey(key);
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
        final Optional<ConfigurationEntity> read;
        final String                        value;

        read = configurationRepository.findByKey(key);
        if (read.isPresent()) {
            value = read.get()
                .getValue();
        } else {
            value = "";
        }

        return value;
    }

}
