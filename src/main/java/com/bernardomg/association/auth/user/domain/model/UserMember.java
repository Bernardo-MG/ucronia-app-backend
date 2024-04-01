
package com.bernardomg.association.auth.user.domain.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class UserMember {

    private final UserMemberName name;

    private final Long           number;

    /**
     * User username.
     */
    private final String         username;

}
