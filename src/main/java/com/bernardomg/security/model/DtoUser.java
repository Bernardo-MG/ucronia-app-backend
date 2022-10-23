
package com.bernardomg.security.model;

import lombok.Data;

@Data
public final class DtoUser implements User {

    /**
     * User expired flag.
     */
    private Boolean credentialsExpired = false;

    /**
     * User email.
     */
    private String  email;

    /**
     * User enabled flag.
     */
    private Boolean enabled            = true;

    /**
     * User expired flag.
     */
    private Boolean expired            = false;

    /**
     * User id.
     */
    private Long    id;

    /**
     * User locked flag.
     */
    private Boolean locked             = false;

    /**
     * User name.
     */
    private String  username;

}
