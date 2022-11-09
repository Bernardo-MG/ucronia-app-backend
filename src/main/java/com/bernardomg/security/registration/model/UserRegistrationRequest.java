
package com.bernardomg.security.registration.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class UserRegistrationRequest {

    /**
     * User email.
     */
    @NotNull
    private String email;

    /**
     * User name.
     */
    @NotNull
    private String username;

}
