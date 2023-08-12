
package com.bernardomg.security.user.authorization.test.util.model;

import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.ValidatedRoleQuery;

public final class RolesQuery {

    public static final RoleQuery empty() {
        return ValidatedRoleQuery.builder()
            .build();
    }

}
