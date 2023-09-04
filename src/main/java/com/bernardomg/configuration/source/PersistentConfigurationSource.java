
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
    public final String getConfiguration(final String key) {
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
