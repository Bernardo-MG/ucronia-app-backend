
package com.bernardomg.association.configuration.test.config.factory;

import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;

public final class Configurations {

    public static final Configuration amount() {
        return Configuration.builder()
            .withKey(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("1.0")
            .build();
    }

    public static final Configuration floatValue() {
        return Configuration.builder()
            .withKey(ConfigurationConstants.KEY)
            .withValue("10.1")
            .build();
    }

    public static final Configuration intValue() {
        return Configuration.builder()
            .withKey(ConfigurationConstants.KEY)
            .withValue("10")
            .build();
    }

    public static final Configuration stringValue() {
        return Configuration.builder()
            .withKey(ConfigurationConstants.KEY)
            .withValue("value")
            .build();
    }

    public static final Configuration valid() {
        return Configuration.builder()
            .withKey(ConfigurationConstants.KEY)
            .withValue(ConfigurationConstants.VALUE)
            .build();
    }

    private Configurations() {
        super();
    }

}
