
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.DtoAction;
import com.bernardomg.security.user.model.request.ActionQuery;
import com.bernardomg.security.user.persistence.model.PersistentAction;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    public DtoAction toDto(final PersistentAction entity);

    @Mapping(target = "id", ignore = true)
    public PersistentAction toEntity(final ActionQuery data);

}
