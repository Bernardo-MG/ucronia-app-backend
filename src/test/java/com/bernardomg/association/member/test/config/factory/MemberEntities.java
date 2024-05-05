
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;

public final class MemberEntities {

    public static final MemberEntity missingSurname() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.missingSurname())
            .build();
    }

    public static final MemberEntity nameChange() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.nameChange())
            .build();
    }

    public static final MemberEntity valid() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.valid())
            .build();
    }

    public static final MemberEntity valid(final int index) {
        return MemberEntity.builder()
            .withPerson(PersonEntities.valid(index))
            .build();
    }

}
