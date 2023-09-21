
package com.bernardomg.association.membership.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.membership.fee.model.ImmutableMemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;
import com.bernardomg.association.membership.fee.persistence.model.PersistentMemberFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    @Mapping(target = "memberName", ignore = true)
    public ImmutableMemberFee toDto(final PersistentFee entity);

    public ImmutableMemberFee toDto(final PersistentMemberFee entity);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

}
