
package com.bernardomg.association.library.author.test.configuration.factory;

import com.bernardomg.association.library.author.domain.model.Author;

public final class Authors {

    public static final Author emptyName() {
        return new Author(AuthorConstants.NUMBER, " ");
    }

    public static final Author padded() {
        return new Author(-1L, " " + AuthorConstants.NAME + " ");
    }

    public static final Author toCreate() {
        return new Author(-1L, AuthorConstants.NAME);
    }

    public static final Author valid() {
        return new Author(AuthorConstants.NUMBER, AuthorConstants.NAME);
    }

}
