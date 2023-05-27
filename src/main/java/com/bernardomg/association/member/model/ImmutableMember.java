
package com.bernardomg.association.member.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public final class ImmutableMember implements Member {

    private final Boolean active;

    private final Long    id;

    private final String  identifier;

    private final String  name;

    private final String  phone;

    private final String  surname;

}
