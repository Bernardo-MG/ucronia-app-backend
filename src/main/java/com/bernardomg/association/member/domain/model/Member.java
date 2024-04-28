
package com.bernardomg.association.member.domain.model;

import lombok.Builder;
import lombok.Value;

/**
 * TODO: Try to make this immutable.
 */
@Value
@Builder(setterPrefix = "with")
public final class Member {

    private final boolean    active;

    private final String     identifier;

    private final MemberName name;

    private final long       number;

    private final String     phone;

}
