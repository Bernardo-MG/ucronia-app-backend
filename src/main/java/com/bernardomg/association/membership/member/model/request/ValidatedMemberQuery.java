
package com.bernardomg.association.membership.member.model.request;

import com.bernardomg.association.membership.member.model.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedMemberQuery implements MemberQuery {

    private Long         id;

    private String       identifier;

    private String       name;

    private String       phone;

    @Builder.Default
    private MemberStatus status = MemberStatus.ALL;

    private String       surname;

}
