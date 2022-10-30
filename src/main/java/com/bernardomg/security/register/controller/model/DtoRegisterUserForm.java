
package com.bernardomg.security.register.controller.model;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoRegisterUserForm {

    /**
     * User email.
     */
    @NotNull
    private String email;

    /**
     * User password.
     */
    @NotNull
    private String password;

    /**
     * User name.
     */
    @NotNull
    private String username;

}
