
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoMember implements Member {

    private Boolean active;

    private Long    id   = -1L;

    private String  name = "";

}
