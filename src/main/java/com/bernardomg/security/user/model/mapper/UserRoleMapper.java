
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.authorization.persistence.model.PersistentUserRole;
import com.bernardomg.security.user.model.DtoUserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    public DtoUserRole toDto(final PersistentUserRole entity);

}
