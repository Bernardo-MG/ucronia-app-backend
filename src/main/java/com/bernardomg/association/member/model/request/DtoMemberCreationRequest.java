
package com.bernardomg.association.member.model.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class DtoMemberCreationRequest implements MemberCreationRequest {

    @NotNull
    private Boolean active;

    private String  identifier;

    @NotEmpty
    @NotNull
    private String  name;

    private String  phone;

    private String  surname;

}
