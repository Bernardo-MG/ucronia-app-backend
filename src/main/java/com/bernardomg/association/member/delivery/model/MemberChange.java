
package com.bernardomg.association.member.delivery.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MemberChange {

    private String           identifier;

    @NotNull
    @Valid
    private MemberChangeName name;

    private String           phone;

}
