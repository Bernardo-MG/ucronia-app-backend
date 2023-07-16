
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.RolePermission;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

    public RolePermission toDto(final PersistentRolePermission entity);

}
