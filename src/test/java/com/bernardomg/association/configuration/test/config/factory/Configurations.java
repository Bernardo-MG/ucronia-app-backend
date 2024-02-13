
package com.bernardomg.association.configuration.test.config.factory;

import com.bernardomg.association.configuration.domain.model.Configuration;
import com.bernardomg.association.configuration.usecase.AssociationConfigurationKey;

public final class Configurations {

    public static final Configuration amount() {
        return Configuration.builder()
            .withKey(AssociationConfigurationKey.FEE_AMOUNT)
            .withValue("2.0")
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
