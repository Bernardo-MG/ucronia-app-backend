
package com.bernardomg.association.library.publisher.test.configuration.factory;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public final class Publishers {

    public static final Publisher emptyName() {
        return Publisher.builder()
            .withName(" ")
            .build();
    }

    public static final Publisher valid() {
        return Publisher.builder()
            .withName(PublisherConstants.NAME)
            .build();
    }

}
