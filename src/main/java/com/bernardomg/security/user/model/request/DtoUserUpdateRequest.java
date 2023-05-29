
package com.bernardomg.security.user.model.request;

import com.bernardomg.security.user.model.User;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoUserUpdateRequest implements User {

    /**
     * User expired flag.
     */
    @NotNull
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
    private Boolean enabled;

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean expired;

    /**
     * User id.
     */
    @NotNull
    private Long    id;

    /**
     * User locked flag.
     */
    @NotNull
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
