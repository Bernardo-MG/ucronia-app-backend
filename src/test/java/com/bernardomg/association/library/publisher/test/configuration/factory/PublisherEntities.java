
package com.bernardomg.association.library.publisher.test.configuration.factory;

import com.bernardomg.association.library.publisher.adapter.inbound.jpa.model.PublisherEntity;

public final class PublisherEntities {

    public static final PublisherEntity valid() {
        final PublisherEntity entity = new PublisherEntity();

        entity.setNumber(PublisherConstants.NUMBER);
        entity.setName(PublisherConstants.NAME);

        return entity;
    }

    private PublisherEntities() {
        super();
    }

}
