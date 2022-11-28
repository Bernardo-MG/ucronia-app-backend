
package com.bernardomg.security.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableRolePrivilege implements RolePrivilege {

    private final Long privilege;

    private final Long role;

    public ImmutableRolePrivilege(@NonNull final Long rl, @NonNull final Long priv) {
        super();

        role = rl;
        privilege = priv;
    }

}
