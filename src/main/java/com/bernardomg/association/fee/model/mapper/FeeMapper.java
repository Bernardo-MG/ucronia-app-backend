
package com.bernardomg.association.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.fee.model.request.FeeCreate;
import com.bernardomg.association.fee.model.request.FeeUpdate;
import com.bernardomg.association.fee.persistence.model.PersistentFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeCreate request);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

}
