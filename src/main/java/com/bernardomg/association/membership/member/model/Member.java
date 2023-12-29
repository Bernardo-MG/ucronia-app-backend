
package com.bernardomg.association.membership.member.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Try to make this immutable.
 */
@Data
@Builder
public final class Member {

    private boolean active;

    private Long    id;

    private String  identifier;

    private String  name;

    private String  phone;

    private String  surname;

}
