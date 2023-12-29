
package com.bernardomg.association.membership.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.membership.member.model.Member;
import com.bernardomg.association.membership.member.model.MemberChange;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    @Mapping(target = "active", ignore = true)
    public Member toDto(final MemberEntity entity);

    @Mapping(target = "id", ignore = true)
    public MemberEntity toEntity(final MemberChange data);

}
