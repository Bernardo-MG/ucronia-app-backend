
package com.bernardomg.security.user.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.security.permission.persistence.model.PersistentResource;
import com.bernardomg.security.user.model.DtoResource;
import com.bernardomg.security.user.model.request.ResourceQuery;

@Mapper(componentModel = "spring")
public interface ResourceMapper {

    public DtoResource toDto(final PersistentResource entity);

    @Mapping(target = "id", ignore = true)
    public PersistentResource toEntity(final ResourceQuery data);

}
