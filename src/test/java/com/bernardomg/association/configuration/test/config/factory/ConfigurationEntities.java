
package com.bernardomg.association.configuration.test.config.factory;

import com.bernardomg.association.configuration.adapter.inbound.jpa.model.ConfigurationEntity;

public final class ConfigurationEntities {

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
