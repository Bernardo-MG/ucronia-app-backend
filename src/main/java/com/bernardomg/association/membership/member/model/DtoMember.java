
package com.bernardomg.association.membership.member.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class DtoMember implements Member {

    private Boolean active;

    private Long    id;

    private String  identifier;

    private String  name;

    private String  phone;

    private String  surname;

}
