
package com.bernardomg.association.library.publisher.test.configuration.factory;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public final class Publishers {

    public static final Publisher emptyName() {
        return new Publisher(PublisherConstants.NUMBER, " ");
    }

    public static final Publisher toCreate() {
        return new Publisher(-1L, PublisherConstants.NAME);
    }

    public static final Publisher valid() {
        return new Publisher(PublisherConstants.NUMBER, PublisherConstants.NAME);
    }

}
