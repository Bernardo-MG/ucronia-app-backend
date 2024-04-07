
package com.bernardomg.association.security.user.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Try to make this immutable.
 */
@Data
@Builder(setterPrefix = "with")
public final class UserMemberName {

    private String firstName;

    // TODO: should be generated automatically
    private String fullName;

    private String lastName;

}
