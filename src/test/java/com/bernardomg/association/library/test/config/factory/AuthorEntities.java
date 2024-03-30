
package com.bernardomg.association.library.test.config.factory;

import com.bernardomg.association.library.adapter.inbound.jpa.model.AuthorEntity;

public final class AuthorEntities {

    public static final AuthorEntity valid() {
        return AuthorEntity.builder()
            .withName(AuthorConstants.NAME)
            .build();
    }

}
