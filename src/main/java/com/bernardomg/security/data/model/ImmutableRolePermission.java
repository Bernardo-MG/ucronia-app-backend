
package com.bernardomg.security.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableRolePermission implements RolePermission {

    private final Long action;

    private final Long resource;

    private final Long role;

    public ImmutableRolePermission(@NonNull final Long rl, @NonNull final Long res, @NonNull final Long priv) {
        super();

        role = rl;
        resource = res;
        action = priv;
    }

}
