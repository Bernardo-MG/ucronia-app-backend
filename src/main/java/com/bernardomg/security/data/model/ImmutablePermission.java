
package com.bernardomg.security.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutablePermission implements Permission {

    private final String action;

    private final Long   actionId;

    private final String resource;

    private final Long   resourceId;

    public ImmutablePermission(final Long resId, @NonNull final String res, final Long actId,
            @NonNull final String act) {
        super();

        resourceId = resId;
        resource = res;
        actionId = actId;
        action = act;
    }

}
