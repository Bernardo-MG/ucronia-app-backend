
package com.bernardomg.security.user.permission.model;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ImmutablePermissionsSet implements PermissionsSet {

    private final Map<String, List<String>> permissions;

    private final String                    username;

}
