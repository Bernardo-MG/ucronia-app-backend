
package com.bernardomg.association.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class ValidatedMemberUpdate implements MemberUpdate {

    @NotNull
    private Boolean active;

    private String  identifier;

    @NotEmpty
    private String  name;

    private String  phone;

    private String  surname;

}
