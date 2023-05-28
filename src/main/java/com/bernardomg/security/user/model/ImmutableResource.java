
package com.bernardomg.security.user.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableResource implements Resource {

    private final Long   id;

    private final String name;

}
