
package com.bernardomg.security.permission.authorization;

public interface AuthorizedResourceValidator {

    public boolean isAuthorized(final String resource, final String action);

}
