
package com.bernardomg.security.permission.service;

import com.bernardomg.security.permission.model.PermissionsSet;

public interface PermissionService {

    public PermissionsSet getPermissions(final String username);

}
