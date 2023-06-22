
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.ImmutablePermission;
import com.bernardomg.security.user.model.ImmutableRole;
import com.bernardomg.security.user.model.Permission;
import com.bernardomg.security.user.model.Role;
import com.bernardomg.security.user.model.request.RoleCreate;
import com.bernardomg.security.user.model.request.RoleQuery;
import com.bernardomg.security.user.model.request.RoleUpdate;
import com.bernardomg.security.user.persistence.model.PersistentRole;
import com.bernardomg.security.user.persistence.model.PersistentRoleGrantedPermission;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    public default Role toDto(final PersistentRole entity) {
        return ImmutableRole.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    public default Permission toDto(final PersistentRoleGrantedPermission entity) {
        return ImmutablePermission.builder()
            .actionId(entity.getActionId())
            .action(entity.getAction())
            .resourceId(entity.getResourceId())
            .resource(entity.getResource())
            .build();
    }

    @Mapping(target = "id", ignore = true)
    public PersistentRole toEntity(final RoleCreate data);

    @Mapping(target = "id", ignore = true)
    public PersistentRole toEntity(final RoleQuery data);

    public PersistentRole toEntity(final RoleUpdate data);

}
