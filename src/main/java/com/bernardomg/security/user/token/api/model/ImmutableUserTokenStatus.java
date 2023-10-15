
package com.bernardomg.security.user.token.api.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class ImmutableUserTokenStatus implements UserTokenStatus {

    private final String  username;

    @NonNull
    private final Boolean valid;

}
