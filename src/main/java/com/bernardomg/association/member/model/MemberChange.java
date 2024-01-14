
package com.bernardomg.association.member.model;

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
    private MemberChangeName name;

    private String           phone;

}