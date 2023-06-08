
package com.bernardomg.security.user.model;

import java.util.List;
import java.util.Map;

public interface PermissionsSet {

    public Map<String, List<String>> getPermissions();

    public String getUsername();

}
