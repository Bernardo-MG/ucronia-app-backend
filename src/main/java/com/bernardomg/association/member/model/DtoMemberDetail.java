
package com.bernardomg.association.member.model;

import lombok.Data;

@Data
public final class DtoMemberDetail implements MemberDetail {

    private Long                   id   = -1L;

    private Iterable<MemberMonth>  months;

    private String                 name = "";

    private Iterable<MemberPeriod> periods;

}
