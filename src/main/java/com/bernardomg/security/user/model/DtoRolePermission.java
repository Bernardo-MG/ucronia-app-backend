
package com.bernardomg.security.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoRolePermission implements RolePermission {

    private final Long permissionId;

    private final Long roleId;

}
