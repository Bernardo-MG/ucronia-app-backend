
package com.bernardomg.association.library.publisher.test.configuration.factory;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;

public final class PublisherEntities {

    public static final PublisherEntity valid() {
        return PublisherEntity.builder()
            .withName(PublisherConstants.NAME)
            .build();
    }

}
