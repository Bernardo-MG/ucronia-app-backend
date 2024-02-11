
package com.bernardomg.association.configuration.usecase.service;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.persistence.model.PersistentConfiguration;
import com.bernardomg.configuration.persistence.repository.ConfigurationRepository;
import com.bernardomg.configuration.source.ConfigurationSource;

public final class DefaultAssociationConfigurationService implements AssociationConfigurationService {

    private final ConfigurationRepository configurationRepository;

    private final ConfigurationSource     configurationSource;

    public DefaultAssociationConfigurationService(final ConfigurationSource configSource,
            final ConfigurationRepository configRepository) {
        super();

        configurationSource = Objects.requireNonNull(configSource);
        configurationRepository = Objects.requireNonNull(configRepository);
    }

    @Override
    public final AssociationConfiguration read() {
        final float feeAmount;

        feeAmount = configurationSource.getFloat(AssociationConfigurationKey.FEE_AMOUNT);

        return AssociationConfiguration.builder()
            .withFeeAmount(feeAmount)
            .build();
    }

    @Override
    public final void update(final AssociationConfiguration request) {
        final PersistentConfiguration           config;
        final Optional<PersistentConfiguration> found;

        found = configurationRepository.findOneByKey(AssociationConfigurationKey.FEE_AMOUNT);
        if (found.isPresent()) {
            config = found.get();
            config.setValue(String.valueOf(request.getFeeAmount()));
        } else {
            config = PersistentConfiguration.builder()
                .withKey(AssociationConfigurationKey.FEE_AMOUNT)
                .withValue(String.valueOf(request.getFeeAmount()))
                .build();
        }
        configurationRepository.save(config);
    }

}
