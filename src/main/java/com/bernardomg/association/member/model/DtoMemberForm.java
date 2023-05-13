
package com.bernardomg.association.member.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoMemberForm implements MemberForm {

    @NotNull
    private Boolean active;

    @NotNull
    private String  identifier;

    @NotEmpty
    @NotNull
    private String  name;

    @NotNull
    private String  phone;

    @NotNull
    private String  surname;

}
