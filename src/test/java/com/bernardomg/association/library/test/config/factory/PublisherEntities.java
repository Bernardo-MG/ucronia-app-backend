
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.PublisherEntity;

public final class PublisherEntities {

    public static final PublisherEntity valid() {
        return PublisherEntity.builder()
            .withName(PublisherConstants.NAME)
            .build();
    }

}
