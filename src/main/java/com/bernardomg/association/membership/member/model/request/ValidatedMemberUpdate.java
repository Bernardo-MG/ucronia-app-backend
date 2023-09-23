
package com.bernardomg.association.membership.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ValidatedMemberUpdate implements MemberUpdate {

    @NotNull
    private Boolean active;

    private String  identifier;

    @NotEmpty
    private String  name;

    private String  phone;

    private String  surname;

}
