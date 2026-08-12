
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Key;

public final class Keys {

    public static final Key valid() {
        return new Key(KeyConstants.NUMBER, false, "Main entrance key");
    }

    private Keys() {
        super();
    }

}
