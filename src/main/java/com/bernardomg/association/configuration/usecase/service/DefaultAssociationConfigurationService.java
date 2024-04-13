
package com.bernardomg.association.configuration.usecase.service;

import java.util.Objects;

import org.springframework.transaction.annotation.Transactional;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.domain.model.Configuration;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

@Transactional
public final class DefaultAssociationConfigurationService implements AssociationConfigurationService {

    private final ConfigurationRepository configurationRepository;

    public DefaultAssociationConfigurationService(final ConfigurationRepository configRepository) {
        super();

        configurationRepository = Objects.requireNonNull(configRepository);
    }

    @Override
    public final AssociationConfiguration getAll() {
        final float feeAmount;

        feeAmount = configurationRepository.getFloat(AssociationConfigurationKey.FEE_AMOUNT);

        return AssociationConfiguration.builder()
            .withFeeAmount(feeAmount)
            .build();
    }

    @Override
    public final void update(final AssociationConfiguration configuration) {
        final Configuration toSave;

        // TODO: Should verify it exists?

        toSave = Configuration.builder()
            .withKey(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue(String.valueOf(configuration.getFeeAmount()))
            .build();

        configurationRepository.save(toSave);
    }

}
