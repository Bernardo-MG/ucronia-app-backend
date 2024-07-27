
package com.bernardomg.association.library.author.test.config.factory;

import com.bernardomg.association.library.author.domain.model.Author;

public final class Authors {

    public static final Author emptyName() {
        return Author.builder()
            .withName(" ")
            .build();
    }

    public static final Author valid() {
        return Author.builder()
            .withName(AuthorConstants.NAME)
            .build();
    }

}
