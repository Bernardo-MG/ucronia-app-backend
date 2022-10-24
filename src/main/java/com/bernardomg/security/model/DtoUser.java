
package com.bernardomg.security.model;

import lombok.Data;

@Data
public final class DtoUser implements User {

    /**
     * User expired flag.
     */
    private Boolean credentialsExpired;

    /**
     * User email.
     */
    private String  email;

    /**
     * User enabled flag.
     */
    private Boolean enabled;

    /**
     * User expired flag.
     */
    private Boolean expired;

    /**
     * User id.
     */
    private Long    id;

    /**
     * User locked flag.
     */
    private Boolean locked;

    /**
     * User name.
     */
    private String  username;

}
