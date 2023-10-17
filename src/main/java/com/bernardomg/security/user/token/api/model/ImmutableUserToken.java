
package com.bernardomg.security.user.token.api.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public final class ImmutableUserToken implements UserToken {

    private boolean       consumed;

    @NonNull
    private LocalDateTime creationDate;

    @NonNull
    private LocalDateTime expirationDate;

    @NonNull
    private Long          id;

    @NonNull
    private final String  name;

    private boolean       revoked;

    @NonNull
    private final String  scope;

    @NonNull
    private final String  token;

    @NonNull
    private final String  username;

}
