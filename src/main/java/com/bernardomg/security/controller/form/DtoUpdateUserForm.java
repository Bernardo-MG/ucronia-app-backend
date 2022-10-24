
package com.bernardomg.security.controller.form;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.model.User;

import lombok.Data;

@Data
public final class DtoUpdateUserForm implements User {

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
    @NotNull
    private String  username;

}
