
package com.bernardomg.security.password.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class PasswordResetRequest {

    /**
     * User name.
     */
    @NotNull
    private String password;

    /**
     * User email.
     */
    @NotNull
    private String username;

}
