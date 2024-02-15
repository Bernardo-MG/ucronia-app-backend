
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.domain.model.Author;

public final class Authors {

    public static final Author valid() {
        return Author.builder()
            .withName(AuthorConstants.NAME)
            .build();
    }

}
