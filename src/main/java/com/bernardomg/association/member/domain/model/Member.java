
package com.bernardomg.association.member.domain.model;

import lombok.Builder;
import lombok.Data;

/**
 * TODO: Try to make this immutable.
 */
@Data
@Builder
public final class Member {

    private boolean    active;

    private String     identifier;

    private MemberName name;

    private long       number;

    private String     phone;

}
