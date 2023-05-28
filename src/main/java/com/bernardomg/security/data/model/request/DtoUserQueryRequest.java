
package com.bernardomg.security.data.model.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoUserQueryRequest implements UserQueryRequest {

    /**
     * User expired flag.
     */
    private final Boolean credentialsExpired;

    /**
     * User email.
     */
    private final String  email;

    /**
     * User enabled flag.
     */
    private final Boolean enabled;

    /**
     * User expired flag.
     */
    private final Boolean expired;

    /**
     * User locked flag.
     */
    private final Boolean locked;

    /**
     * User name.
     */
    private final String  name;

    /**
     * User username.
     */
    private final String  username;

}
