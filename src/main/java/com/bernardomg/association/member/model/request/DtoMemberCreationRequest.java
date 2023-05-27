
package com.bernardomg.association.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoMemberCreationRequest implements MemberCreationRequest {

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
