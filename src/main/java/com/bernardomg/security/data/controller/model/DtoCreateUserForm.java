
package com.bernardomg.security.data.controller.model;

import javax.validation.constraints.NotNull;

import com.bernardomg.security.data.model.User;

import lombok.Data;

@Data
public final class DtoCreateUserForm implements User {

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean    credentialsExpired;

    /**
     * User email.
     */
    @NotNull
    private String     email;

    /**
     * User enabled flag.
     */
    @NotNull
    private Boolean    enabled;

    /**
     * User expired flag.
     */
    @NotNull
    private Boolean    expired;

    /**
     * User id.
     */
    @NotNull
    private final Long id = null;

    /**
     * User locked flag.
     */
    @NotNull
    private Boolean    locked;

    /**
     * User name.
     */
    @NotNull
    private String     username;

}
