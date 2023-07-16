
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.DtoRolePermission;
import com.bernardomg.security.user.persistence.model.PersistentRolePermission;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

    public DtoRolePermission toDto(final PersistentRolePermission entity);

}
