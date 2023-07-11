
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;

import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public DtoUser toDto(final PersistentUser entity);

    public PersistentUser toEntity(final UserCreate data);

    public PersistentUser toEntity(final UserQuery data);

    public PersistentUser toEntity(final UserUpdate data);

}
