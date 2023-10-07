
package com.bernardomg.security.auth.permission;

public interface AuthorizedResourceValidator {

    public boolean isAuthorized(final String resource, final String action);

}
