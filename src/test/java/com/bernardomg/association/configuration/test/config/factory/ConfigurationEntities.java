
package com.bernardomg.association.configuration.test.config.factory;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;

public final class ConfigurationEntities {

    public static final ConfigurationEntity amount() {
        return ConfigurationEntity.builder()
            .withKey(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("2.0")
            .build();
    }

    public static final ConfigurationEntity valid() {
        return ConfigurationEntity.builder()
            .withKey(ConfigurationConstants.KEY)
            .withValue(ConfigurationConstants.VALUE)
            .build();
    }

    private ConfigurationEntities() {
        super();
    }

}
