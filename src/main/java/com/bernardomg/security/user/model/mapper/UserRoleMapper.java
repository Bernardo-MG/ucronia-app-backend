
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.DtoUserRole;
import com.bernardomg.security.user.persistence.model.PersistentUserRole;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {

    public DtoUserRole toDto(final PersistentUserRole entity);

}
