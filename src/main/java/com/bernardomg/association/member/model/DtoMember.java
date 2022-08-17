
package com.bernardomg.association.member.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public final class DtoMember implements Member {

    @NotNull
    private Boolean active;

    private Long    id;

    @NotEmpty
    @NotNull
    private String  identifier;

    @NotEmpty
    @NotNull
    private String  name;

    @NotEmpty
    @NotNull
    private String  phone;

    @NotEmpty
    @NotNull
    private String  surname;

}
