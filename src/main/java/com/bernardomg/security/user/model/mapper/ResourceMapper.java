
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.user.model.ImmutableResource;
import com.bernardomg.security.user.model.Resource;
import com.bernardomg.security.user.model.request.ResourceQuery;
import com.bernardomg.security.user.persistence.model.PersistentResource;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    public default Resource toDto(final PersistentResource entity) {
        return ImmutableResource.builder()
            .id(entity.getId())
            .name(entity.getName())
            .build();
    }

    @Mapping(target = "id", ignore = true)
    public PersistentResource toEntity(final ResourceQuery data);

}
