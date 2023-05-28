
package com.bernardomg.security.user.service;

import com.bernardomg.security.user.model.PermissionsSet;

public interface PermissionService {

    public PermissionsSet getPermissions(final String username);

}
