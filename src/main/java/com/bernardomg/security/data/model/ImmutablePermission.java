
package com.bernardomg.security.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutablePermission implements Permission {

    private final String action;

    private final String resource;

    public ImmutablePermission(@NonNull final String res, @NonNull final String priv) {
        super();

        resource = res;
        action = priv;
    }

}
