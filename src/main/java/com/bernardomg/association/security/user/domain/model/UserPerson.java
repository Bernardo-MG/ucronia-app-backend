
package com.bernardomg.association.security.user.domain.model;

import lombok.Builder;

@Builder(setterPrefix = "with")
public record UserPerson(Long number, String username) {

}
