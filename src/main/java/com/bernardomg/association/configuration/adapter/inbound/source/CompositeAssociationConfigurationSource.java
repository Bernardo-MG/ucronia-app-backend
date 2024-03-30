
package com.bernardomg.association.configuration.adapter.inbound.source;

import java.util.Objects;

import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.association.configuration.usecase.source.AssociationConfigurationSource;
import com.bernardomg.association.configuration.usecase.source.ConfigurationSource;

public final class CompositeAssociationConfigurationSource implements AssociationConfigurationSource {

    private final ConfigurationSource configurationSource;

    public CompositeAssociationConfigurationSource(final ConfigurationSource confSource) {
        super();

        configurationSource = Objects.requireNonNull(confSource);
    }

    @Override
    public final Float getFeeAmount() {
        return configurationSource.getFloat(AssociationConfigurationKey.FEE_AMOUNT);
    }

}
