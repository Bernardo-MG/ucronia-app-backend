
package com.bernardomg.association.membership.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.membership.member.model.DtoMember;
import com.bernardomg.association.membership.member.model.request.MemberCreate;
import com.bernardomg.association.membership.member.model.request.MemberQuery;
import com.bernardomg.association.membership.member.model.request.MemberUpdate;
import com.bernardomg.association.membership.member.persistence.model.MemberEntity;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    public DtoMember toDto(final MemberEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    public MemberEntity toEntity(final MemberCreate data);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    public MemberEntity toEntity(final MemberQuery data);

    @Mapping(target = "id", ignore = true)
    public MemberEntity toEntity(final MemberUpdate data);

}
