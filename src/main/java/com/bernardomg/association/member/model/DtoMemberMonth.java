
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoMemberMonth implements MemberMonth {

    private Long    id;

    private Long    member;

    private Integer month;

    private Integer year;

}
