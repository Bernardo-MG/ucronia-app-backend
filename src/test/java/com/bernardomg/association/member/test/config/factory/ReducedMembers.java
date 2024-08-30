
package com.bernardomg.association.member.test.config.factory;

import com.bernardomg.association.member.domain.model.PublicMember;
import com.bernardomg.association.person.domain.model.PersonName;
import com.bernardomg.association.person.test.config.factory.PersonConstants;

public final class ReducedMembers {

    public static final PublicMember active() {
        final PersonName name;

        name = PersonName.builder()
            .withFirstName(PersonConstants.NAME)
            .withLastName(PersonConstants.LAST_NAME)
            .build();
        return PublicMember.builder()
            .withNumber(PersonConstants.NUMBER)
            .withName(name)
            .withActive(true)
            .build();
    }

}
