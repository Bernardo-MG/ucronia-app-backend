
package com.bernardomg.security.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutablePermission implements Permission {

    private final String action;

    private final Long   actionId;

    private final String resource;

    private final Long   resourceId;

}
