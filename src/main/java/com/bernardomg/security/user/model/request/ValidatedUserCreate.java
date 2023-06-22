
package com.bernardomg.security.user.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedUserCreate implements UserCreate {

    /**
     * User expired flag.
     */
    @NotNull
    @Deprecated
    private Boolean credentialsExpired;

    /**
     * User email.
     */
    @NotNull
    private String  email;

    /**
     * User enabled flag.
     */
    @NotNull
    @Deprecated
    private Boolean enabled;

    /**
     * User expired flag.
     */
    @NotNull
    @Deprecated
    private Boolean expired;

    /**
     * User locked flag.
     */
    @NotNull
    @Deprecated
    private Boolean locked;

    /**
     * User name.
     */
    private String  name;

    /**
     * User username.
     */
    private String  username;

}
