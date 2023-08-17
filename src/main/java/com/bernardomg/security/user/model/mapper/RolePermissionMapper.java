
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.authorization.persistence.model.PersistentRolePermission;
import com.bernardomg.security.user.model.DtoRolePermission;

@Mapper(componentModel = "spring")
public interface RolePermissionMapper {

    public DtoRolePermission toDto(final PersistentRolePermission entity);

}
