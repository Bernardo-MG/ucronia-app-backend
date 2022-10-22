
package com.bernardomg.security.profile.model;

import com.bernardomg.security.permission.model.SecurityPermission;

public interface SecurityProfile {

    public Long getId();

    public String getName();

    public Iterable<SecurityPermission> getPermissions();

}
