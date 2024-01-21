
package com.bernardomg.association.member.domain.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MemberChangeName {

    @NotEmpty
    private String firstName;

    private String lastName;

}
