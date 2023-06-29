
package com.bernardomg.association.member.model.request;

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
public final class ValidatedMemberCreate implements MemberCreate {

    @NotNull
    private Boolean active;

    private String  identifier;

    @NotEmpty
    private String  name;

    private String  phone;

    private String  surname;

}
