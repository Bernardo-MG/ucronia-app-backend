
package com.bernardomg.association.member.domain.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class MemberChange {

    private String           identifier;

    @NotNull
    @Valid
    private MemberChangeName name;

    private String           phone;

}
