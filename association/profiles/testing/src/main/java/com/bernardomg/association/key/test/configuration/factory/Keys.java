
package com.bernardomg.association.key.test.configuration.factory;

import com.bernardomg.association.key.domain.model.Key;

public final class Keys {

    public static final Key available() {
        return new Key(KeyConstants.NUMBER, true, KeyConstants.DESCRIPTION);
    }

    public static final Key created() {
        return new Key(1L, true, KeyConstants.DESCRIPTION);
    }

    public static final Key descriptionChange() {
        return new Key(KeyConstants.NUMBER, true, KeyConstants.DESCRIPTION_CHANGE);
    }

    private Keys() {
        super();
    }

}
