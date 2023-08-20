
package com.bernardomg.security.permission.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.permission.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.model.DtoRolePermission;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

    public DtoRolePermission toDto(final PersistentRolePermission entity);

}
