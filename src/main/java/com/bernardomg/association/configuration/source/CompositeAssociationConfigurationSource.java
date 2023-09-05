
package com.bernardomg.association.configuration.source;

import java.util.Objects;

import com.bernardomg.configuration.source.ConfigurationSource;

public final class CompositeAssociationConfigurationSource implements AssociationConfigurationSource {

    private static String             FEE_AMOUNT = "fee.amount";

    private final ConfigurationSource configurationSource;

    public CompositeAssociationConfigurationSource(final ConfigurationSource confSource) {
        super();

        configurationSource = Objects.requireNonNull(confSource);
    }

    @Override
    public final Float getFeeAmount() {
        return configurationSource.getFloat(FEE_AMOUNT);
    }

}
