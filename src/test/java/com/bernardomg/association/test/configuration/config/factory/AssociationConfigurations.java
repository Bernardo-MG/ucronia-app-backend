
package com.bernardomg.association.test.configuration.config.factory;

import com.bernardomg.association.configuration.model.AssociationConfiguration;

public final class AssociationConfigurations {

    public static final AssociationConfiguration amount() {
        return AssociationConfiguration.builder()
            .feeAmount(2)
            .build();
    }

    private AssociationConfigurations() {
        super();
    }

}
