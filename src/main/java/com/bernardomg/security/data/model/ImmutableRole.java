
package com.bernardomg.security.data.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableRole implements Role {

    private final Long   id;

    private final String name;

}
