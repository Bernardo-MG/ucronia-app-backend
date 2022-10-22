
package com.bernardomg.security.model;

import java.util.Collection;
import java.util.Collections;

import lombok.Data;

@Data
public final class DtoUser implements User {

    /**
     * User expired flag.
     */
    private Boolean          credentialsExpired = false;

    /**
     * User email.
     */
    private String           email;

    /**
     * User enabled flag.
     */
    private Boolean          enabled            = true;

    /**
     * User expired flag.
     */
    private Boolean          expired            = false;

    /**
     * User id.
     */
    private Long             id;

    /**
     * User locked flag.
     */
    private Boolean          locked             = false;


    private Collection<Role> roles              = Collections.emptyList();

    /**
     * User name.
     */
    private String           username;

}
