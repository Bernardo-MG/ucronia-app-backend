
package com.bernardomg.association.library.author.test.configuration.factory;

import com.bernardomg.association.library.author.adapter.inbound.jpa.model.AuthorEntity;

public final class AuthorEntities {

    public static final AuthorEntity valid() {
        return AuthorEntity.builder()
            .withNumber(AuthorConstants.NUMBER)
            .withName(AuthorConstants.NAME)
            .build();
    }

}
