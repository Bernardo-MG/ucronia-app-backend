
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.adapter.inbound.jpa.model.MemberEntity;
import com.bernardomg.association.person.test.configuration.factory.PersonEntities;

public final class MemberEntities {

    public static final MemberEntity active() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.valid())
            .withActive(true)
            .build();
    }

    public static final MemberEntity inactive() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.valid())
            .withActive(false)
            .build();
    }

    public static final MemberEntity missingLastName() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.missingLastName())
            .withActive(false)
            .build();
    }

    public static final MemberEntity nameChange() {
        return MemberEntity.builder()
            .withPerson(PersonEntities.firstNameChange())
            .withActive(false)
            .build();
    }

    public static final MemberEntity valid(final int index) {
        return MemberEntity.builder()
            .withPerson(PersonEntities.valid(index))
            .withActive(false)
            .build();
    }

}
