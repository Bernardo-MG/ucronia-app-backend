
package com.bernardomg.association.member.model.request;

import com.bernardomg.association.member.model.Member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoMemberCreationRequest implements Member {

    @NotNull
    private Boolean active;

    private Long    id;

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
