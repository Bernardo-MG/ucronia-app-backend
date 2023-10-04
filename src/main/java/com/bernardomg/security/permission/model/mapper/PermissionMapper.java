
package com.bernardomg.security.permission.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.permission.model.DtoPermission;
import com.bernardomg.security.permission.persistence.model.PersistentPermission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    public DtoPermission toDto(final PersistentPermission entity);

}
