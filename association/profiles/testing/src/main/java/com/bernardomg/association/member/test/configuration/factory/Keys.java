
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Key;

public final class Keys {

    public static final Key available() {
        return new Key(KeyConstants.NUMBER, false, KeyConstants.DESCRIPTION);
    }

    public static final Key descriptionChange() {
        return new Key(KeyConstants.NUMBER, false, KeyConstants.DESCRIPTION_CHANGE);
    }

    private Keys() {
        super();
    }

}
