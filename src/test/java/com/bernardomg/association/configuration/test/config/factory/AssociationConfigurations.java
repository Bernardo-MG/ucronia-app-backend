
package com.bernardomg.association.configuration.test.config.factory;

import com.bernardomg.association.configuration.domain.model.AssociationConfiguration;

public final class AssociationConfigurations {

    public static final AssociationConfiguration amount() {
        return AssociationConfiguration.builder()
            .withFeeAmount(1.0F)
            .build();
    }

    private AssociationConfigurations() {
        super();
    }

}
