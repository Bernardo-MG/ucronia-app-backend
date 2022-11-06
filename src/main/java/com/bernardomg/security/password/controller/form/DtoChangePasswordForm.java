
package com.bernardomg.security.password.controller.form;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoChangePasswordForm {

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
