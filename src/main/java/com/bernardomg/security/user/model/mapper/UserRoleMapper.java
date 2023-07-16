
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.UserRole;
import com.bernardomg.security.user.persistence.model.PersistentUserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    public UserRole toDto(final PersistentUserRole entity);

}
