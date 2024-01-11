
package com.bernardomg.association.model.member;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Try to make this immutable.
 */
@Data
@Builder
public final class MemberName {

    private String firstName;

    private String fullName;

    private String lastName;

}
