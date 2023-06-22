
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.Action;
import com.bernardomg.security.user.model.ImmutableAction;
import com.bernardomg.security.user.model.request.ActionQuery;
import com.bernardomg.security.user.persistence.model.PersistentAction;

@Mapper(componentModel = "spring")
public interface ActionMapper {

    public default Action toDto(final PersistentAction entity) {
        return ImmutableAction.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    @Mapping(target = "id", ignore = true)
    public PersistentAction toEntity(final ActionQuery data);

}
