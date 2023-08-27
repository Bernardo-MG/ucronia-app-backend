
package com.bernardomg.association.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedMemberCreate implements MemberCreate {

    @NotEmpty
    private String identifier;

    @NotEmpty
    private String name;

    @NotEmpty
    private String phone;

    @NotEmpty
    private String surname;

}
