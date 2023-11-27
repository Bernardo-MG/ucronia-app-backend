
package com.bernardomg.association.membership.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.membership.fee.model.ImmutableMemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;
import com.bernardomg.association.membership.fee.persistence.model.PersistentFee;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    public ImmutableMemberFee toDto(final MemberFeeEntity entity);

    @Mapping(target = "memberName", ignore = true)
    public ImmutableMemberFee toDto(final PersistentFee entity);

    @Mapping(target = "id", ignore = true)
    public PersistentFee toEntity(final FeeUpdate request);

}
