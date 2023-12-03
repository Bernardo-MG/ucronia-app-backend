
package com.bernardomg.association.membership.fee.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.membership.fee.model.ImmutableMemberFee;
import com.bernardomg.association.membership.fee.model.request.FeeUpdate;
import com.bernardomg.association.membership.fee.persistence.model.FeeEntity;
import com.bernardomg.association.membership.fee.persistence.model.MemberFeeEntity;

@Mapper(componentModel = "spring")
public interface FeeMapper {

    @Mapping(target = "memberName", ignore = true)
    public ImmutableMemberFee toDto(final FeeEntity entity);

    public ImmutableMemberFee toDto(final MemberFeeEntity entity);

    @Mapping(target = "id", ignore = true)
    public FeeEntity toEntity(final FeeUpdate request);

}
