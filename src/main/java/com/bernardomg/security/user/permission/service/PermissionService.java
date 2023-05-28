
package com.bernardomg.security.user.permission.service;

import com.bernardomg.security.user.permission.model.PermissionsSet;

public interface PermissionService {

    public PermissionsSet getPermissions(final String username);

}
