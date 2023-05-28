
package com.bernardomg.security.data.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableUserRole implements UserRole {

    private final Long role;

    private final Long user;

}
