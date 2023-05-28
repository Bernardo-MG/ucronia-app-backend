
package com.bernardomg.security.data.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableRolePermission implements RolePermission {

    private final Long action;

    private final Long resource;

    private final Long role;

}
