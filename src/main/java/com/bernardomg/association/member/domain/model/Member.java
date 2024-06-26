
package com.bernardomg.association.member.domain.model;

import com.bernardomg.association.person.domain.model.PersonName;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public final class Member {

    private final boolean    active;

    private final String     identifier;

    private final PersonName name;

    @Builder.Default
    private final long       number = -1L;

    private final String     phone;

}
