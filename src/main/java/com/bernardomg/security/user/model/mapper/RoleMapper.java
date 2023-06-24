
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.DtoPermission;
import com.bernardomg.security.user.model.DtoRole;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRoleGrantedPermission;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    public DtoRole toDto(final PersistentRole entity);

    public DtoPermission toDto(final PersistentRoleGrantedPermission entity);

    @Mapping(target = "id", ignore = true)
    public PersistentRole toEntity(final RoleCreate data);

    @Mapping(target = "id", ignore = true)
    public PersistentRole toEntity(final RoleQuery data);

    public PersistentRole toEntity(final RoleUpdate data);

}
