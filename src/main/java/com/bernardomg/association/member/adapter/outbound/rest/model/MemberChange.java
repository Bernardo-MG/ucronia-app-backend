
package com.bernardomg.association.member.adapter.outbound.rest.model;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;

import jakarta.validation.constraints.NotNull;

public final record MemberChange(String identifier, @NotNull PersonChangeName name, Boolean active, String phone) {

}
