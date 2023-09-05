
package com.bernardomg.association.configuration.service;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.configuration.AssociationConfigurationKey;
import com.bernardomg.association.configuration.model.request.AssociationConfigurationRequest;
import com.bernardomg.configuration.persistence.model.PersistentConfiguration;
import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;

public final class DefaultAssociationConfigurationService implements AssociationConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public DefaultAssociationConfigurationService(final ConfigurationRepository configRepository) {
        super();

        configurationRepository = Objects.requireNonNull(configRepository);
    }

    @Override
    public final void update(final AssociationConfigurationRequest request) {
        final PersistentConfiguration           config;
        final Optional<PersistentConfiguration> found;

        found = configurationRepository.findOneByKey(AssociationConfigurationKey.FEE_AMOUNT);
        if (found.isPresent()) {
            config = found.get();
            config.setValue(String.valueOf(request.getFeeAmount()));
        } else {
            config = PersistentConfiguration.builder()
                .key(AssociationConfigurationKey.FEE_AMOUNT)
                .value(String.valueOf(request.getFeeAmount()))
                .build();
        }
        configurationRepository.save(config);
    }

}
