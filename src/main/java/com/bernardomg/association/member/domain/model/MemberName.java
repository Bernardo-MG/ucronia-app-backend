
package com.bernardomg.association.member.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Try to make this immutable.
 */
@Data
@Builder(setterPrefix = "with")
public final class MemberName {

    private String firstName;

    private String fullName;

    private String lastName;

}
