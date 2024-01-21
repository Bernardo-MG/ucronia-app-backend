
package com.bernardomg.association.auth.user.domain.model;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class UserMember {

    @NonNull
    private String       fullName;

    /**
     * User name.
     */
    @NonNull
    private final String name;

    @NonNull
    private Long         number;

    /**
     * User username.
     */
    @NonNull
    private final String username;

}
