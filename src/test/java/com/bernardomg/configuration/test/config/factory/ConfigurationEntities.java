
package com.bernardomg.configuration.test.config.factory;

import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.adapter.inbound.jpa.model.ConfigurationEntity;

public final class ConfigurationEntities {

    public static final ConfigurationEntity amount() {
        return ConfigurationEntity.builder()
            .withKey(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("1.0")
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
