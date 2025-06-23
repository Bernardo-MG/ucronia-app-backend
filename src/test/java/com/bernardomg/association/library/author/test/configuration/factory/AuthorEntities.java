
package com.bernardomg.association.library.author.test.configuration.factory;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;

public final class AuthorEntities {

    public static final AuthorEntity valid() {
        final AuthorEntity entity;

        entity = new AuthorEntity();
        entity.setNumber(AuthorConstants.NUMBER);
        entity.setName(AuthorConstants.NAME);

        return entity;
    }

}
