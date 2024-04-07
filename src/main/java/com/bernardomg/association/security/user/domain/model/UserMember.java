
package com.bernardomg.association.security.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class UserMember {

    private final Long   number;

    /**
     * User username.
     */
    private final String username;

}
