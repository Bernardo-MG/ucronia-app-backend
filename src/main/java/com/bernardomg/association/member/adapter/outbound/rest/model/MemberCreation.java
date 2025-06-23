
package com.bernardomg.association.member.adapter.outbound.rest.model;

import com.bernardomg.association.person.adapter.outbound.rest.model.PersonChangeName;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public final record MemberCreation(String identifier, @NotNull @Valid PersonChangeName name, String phone) {

}
