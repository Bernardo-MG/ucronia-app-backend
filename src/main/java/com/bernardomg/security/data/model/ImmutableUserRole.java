
package com.bernardomg.security.data.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutableUserRole implements UserRole {

    private final Long role;

    private final Long user;

    public ImmutableUserRole(@NonNull final Long usr, @NonNull final Long rl) {
        super();

        user = usr;
        role = rl;
    }

}
