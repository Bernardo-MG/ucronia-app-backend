
package com.bernardomg.security.signup.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class SignUpRequest {

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
