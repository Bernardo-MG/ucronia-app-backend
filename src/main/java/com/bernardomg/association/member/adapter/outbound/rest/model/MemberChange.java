
package com.bernardomg.association.member.adapter.outbound.rest.model;

import jakarta.validation.constraints.NotNull;

public final record MemberChange(String identifier, @NotNull PersonChangeName name, Boolean active, String phone) {

}
