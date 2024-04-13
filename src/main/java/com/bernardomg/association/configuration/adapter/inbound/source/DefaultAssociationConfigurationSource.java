
package com.bernardomg.association.configuration.adapter.inbound.source;

import java.util.Objects;

import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.configuration.domain.repository.ConfigurationRepository;

public final class DefaultAssociationConfigurationSource implements AssociationConfigurationSource {

    private final ConfigurationRepository configurationRepository;

    public DefaultAssociationConfigurationSource(final ConfigurationRepository configurationRepo) {
        super();

        configurationRepository = Objects.requireNonNull(configurationRepo);
    }

    @Override
    public final Float getFeeAmount() {
        return configurationRepository.getFloat(AssociationConfigurationKey.FEE_AMOUNT);
    }

}
