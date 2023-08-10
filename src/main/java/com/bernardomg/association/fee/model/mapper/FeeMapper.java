
package com.bernardomg.association.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.fee.model.DtoMemberFee;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;
import com.bernardomg.association.fee.persistence.model.PersistentMemberFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    @Mapping(target = "memberName", ignore = true)
    public DtoMemberFee toDto(final PersistentFee entity);

    public DtoMemberFee toDto(final PersistentMemberFee entity);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

}
