
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

    private String  fullName;

    private String  identifier;

    private String  name;

    private long    number;

    private String  phone;

    private String  surname;

}
