
package com.bernardomg.configuration.test.config.factory;

import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;
import com.bernardomg.configuration.adapter.inbound.jpa.model.ConfigurationEntity;

public final class ConfigurationEntities {

    public static final ConfigurationEntity amount() {
        return ConfigurationEntity.builder()
            .withCode(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("1.0")
            .withType(ConfigurationConstants.NUMBER_TYPE)
            .build();
    }

    public static final ConfigurationEntity valid() {
        return ConfigurationEntity.builder()
            .withCode(ConfigurationConstants.KEY)
            .withValue(ConfigurationConstants.STRING_VALUE)
            .withType(ConfigurationConstants.NUMBER_TYPE)
            .build();
    }

    private ConfigurationEntities() {
        super();
    }

}
