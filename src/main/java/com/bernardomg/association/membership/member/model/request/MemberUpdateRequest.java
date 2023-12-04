
package com.bernardomg.association.membership.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MemberUpdateRequest implements MemberUpdate {

    private String identifier;

    @NotEmpty
    private String name;

    private String phone;

    private String surname;

}
