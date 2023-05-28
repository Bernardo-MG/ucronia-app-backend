
package com.bernardomg.security.user.permission.model;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.NonNull;

@Data
public class ImmutablePermissionsSet implements PermissionsSet {

    private final Map<String, List<String>> permissions;

    private final String                    username;

    public ImmutablePermissionsSet(@NonNull final String user, @NonNull final Map<String, List<String>> perms) {
        super();

        username = user;
        permissions = perms;
    }

}
