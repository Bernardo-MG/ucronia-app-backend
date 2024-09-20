
package com.bernardomg.association.member.adapter.outbound.rest.model;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;

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

    private boolean          active;

    private String           identifier;

    @NotNull
    private PersonChangeName name;

    private String           phone;

}
