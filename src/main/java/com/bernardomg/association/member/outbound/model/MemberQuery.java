
package com.bernardomg.association.member.outbound.model;

import com.bernardomg.association.member.domain.model.MemberStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MemberQuery {

    @Builder.Default
    private MemberStatus status = MemberStatus.ALL;

}
