
package com.bernardomg.security.token.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class ImmutableTokenStatus implements TokenStatus {

    private final String  username;

    @NonNull
    private final Boolean valid;

}
