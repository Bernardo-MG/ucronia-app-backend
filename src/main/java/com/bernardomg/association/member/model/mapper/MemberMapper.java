
package com.bernardomg.association.member.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bernardomg.association.member.model.DtoMember;
import com.bernardomg.association.member.model.request.MemberCreate;
import com.bernardomg.association.member.model.request.MemberQuery;
import com.bernardomg.association.member.model.request.MemberUpdate;
import com.bernardomg.association.member.persistence.model.PersistentMember;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    public DtoMember toDto(final PersistentMember entity);

    @Mapping(target = "id", ignore = true)
    public PersistentMember toEntity(final MemberCreate data);

    @Mapping(target = "id", ignore = true)
    public PersistentMember toEntity(final MemberQuery data);

    @Mapping(target = "id", ignore = true)
    public PersistentMember toEntity(final MemberUpdate data);

}
