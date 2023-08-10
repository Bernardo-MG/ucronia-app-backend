
package com.bernardomg.association.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    @Mapping(target = "name", ignore = true)
    public DtoMemberFee toDto(final PersistentFee entity);

    @Mapping( target = "name", expression = "java(entity.getName() + \" \" + entity.getSurname())")
    public DtoMemberFee toDto(final PersistentMemberFee entity);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

}
