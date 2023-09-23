
package com.bernardomg.association.membership.member.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMember implements Member {

    private final Boolean active;

    private final Long    id;

    private final String  identifier;

    private final String  name;

    private final String  phone;

    private final String  surname;

}
