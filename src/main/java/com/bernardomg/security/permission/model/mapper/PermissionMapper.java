
package com.bernardomg.security.permission.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.permission.model.DtoPermission;
import com.bernardomg.security.permission.persistence.model.PersistentPermission;
import com.bernardomg.security.permission.persistence.model.PersistentRoleGrantedPermission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    public DtoPermission toDto(final PersistentPermission entity);

    @Mapping(target = "id", ignore = true)
    public DtoPermission toDto(final PersistentRoleGrantedPermission entity);

}
