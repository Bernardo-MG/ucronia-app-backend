
package com.bernardomg.security.user.token.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class ImmutableUserToken implements UserToken {

    @NonNull
    private final String scope;

    @NonNull
    private final String username;

}
