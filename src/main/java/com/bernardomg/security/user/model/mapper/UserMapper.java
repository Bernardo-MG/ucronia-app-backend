
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.DtoUser;
import com.bernardomg.security.user.model.request.UserCreate;
import com.bernardomg.security.user.model.request.UserQuery;
import com.bernardomg.security.user.model.request.UserUpdate;
import com.bernardomg.security.user.persistence.model.PersistentUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public DtoUser toDto(final PersistentUser entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordExpired", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "expired", ignore = true)
    @Mapping(target = "locked", ignore = true)
    public PersistentUser toEntity(final UserCreate data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    public PersistentUser toEntity(final UserQuery data);

    @Mapping(target = "expired", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", ignore = true)
    public PersistentUser toEntity(final UserUpdate data);

}
