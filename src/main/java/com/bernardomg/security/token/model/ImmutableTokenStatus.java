
package com.bernardomg.security.token.model;

import lombok.Data;
import lombok.NonNull;

@Data
public final class ImmutableTokenStatus implements TokenStatus {

    private final Boolean valid;

    public ImmutableTokenStatus(@NonNull final Boolean flag) {
        super();

        valid = flag;
    }

}
