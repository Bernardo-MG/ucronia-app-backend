
package com.bernardomg.association.configuration.usecase.service;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.domain.repository.ConfigurationRepository;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.ConfigurationSource;

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
    public final void update(final AssociationConfiguration configuration) {
        final Configuration           toSave;
        final Optional<Configuration> found;

        found = configurationRepository.findOne(AssociationConfigurationKey.FEE_AMOUNT);
        if (found.isPresent()) {
            toSave = found.get();
            toSave.setValue(String.valueOf(configuration.getFeeAmount()));
        } else {
            toSave = Configuration.builder()
                .withKey(AssociationConfigurationKey.FEE_AMOUNT)
                .withValue(String.valueOf(configuration.getFeeAmount()))
                .build();
        }
        configurationRepository.save(toSave);
    }

}
