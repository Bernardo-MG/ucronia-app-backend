
package com.bernardomg.association.library.publisher.test.configuration.factory;

import com.bernardomg.association.library.publisher.domain.model.Publisher;

public final class Publishers {

    public static final Publisher emptyName() {
        return new Publisher(" ");
    }

    public static final Publisher valid() {
        return new Publisher(PublisherConstants.NAME);
    }

}
