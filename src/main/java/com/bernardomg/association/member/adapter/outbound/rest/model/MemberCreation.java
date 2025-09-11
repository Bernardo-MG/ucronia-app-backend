
package com.bernardomg.association.member.adapter.outbound.rest.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public final record MemberCreation(String identifier, @NotNull @Valid PersonChangeName name, String phone) {

}
