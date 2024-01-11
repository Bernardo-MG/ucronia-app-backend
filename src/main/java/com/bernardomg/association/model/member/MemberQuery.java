
package com.bernardomg.association.model.member;

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
