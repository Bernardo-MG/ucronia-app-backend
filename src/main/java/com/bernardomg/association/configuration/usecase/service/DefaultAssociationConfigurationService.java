
package com.bernardomg.association.configuration.usecase.service;

import java.util.Objects;
import java.util.Optional;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.adapter.inbound.jpa.repository.ConfigurationSpringRepository;
import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.ConfigurationSource;

public final class DefaultAssociationConfigurationService implements AssociationConfigurationService {

    private final ConfigurationSpringRepository configurationRepository;

    private final ConfigurationSource           configurationSource;

    public DefaultAssociationConfigurationService(final ConfigurationSource configSource,
            final ConfigurationSpringRepository configRepository) {
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
        final ConfigurationEntity           config;
        final Optional<ConfigurationEntity> found;

        found = configurationRepository.findOneByKey(AssociationConfigurationKey.FEE_AMOUNT);
        if (found.isPresent()) {
            config = found.get();
            config.setValue(String.valueOf(request.getFeeAmount()));
        } else {
            config = ConfigurationEntity.builder()
                .withKey(AssociationConfigurationKey.FEE_AMOUNT)
                .withValue(String.valueOf(request.getFeeAmount()))
                .build();
        }
        configurationRepository.save(config);
    }

}
