
package com.bernardomg.security.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableAction implements Action {

    private final Long   id;

    private final String name;

}
