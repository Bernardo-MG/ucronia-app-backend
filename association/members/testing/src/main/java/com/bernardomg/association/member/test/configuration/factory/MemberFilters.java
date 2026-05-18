
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberStatus;

public final class MemberFilters {

    public static final MemberFilter active() {
        return new MemberFilter(Optional.of(MemberStatus.ACTIVE), Optional.empty());
    }

    public static final MemberFilter all() {
        return new MemberFilter(Optional.of(MemberStatus.ALL), Optional.empty());
    }

    public static final MemberFilter alternativeFirstName() {
        return new MemberFilter(Optional.of(MemberStatus.INACTIVE),
            Optional.of(MemberConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final MemberFilter firstName() {
        return new MemberFilter(Optional.empty(), Optional.of(MemberConstants.FIRST_NAME));
    }

    public static final MemberFilter fullName() {
        return new MemberFilter(Optional.empty(), Optional.of(MemberConstants.FULL_NAME));
    }

    public static final MemberFilter inactive() {
        return new MemberFilter(Optional.of(MemberStatus.INACTIVE), Optional.empty());
    }

    public static final MemberFilter lastName() {
        return new MemberFilter(Optional.empty(), Optional.of(MemberConstants.LAST_NAME));
    }

    public static final MemberFilter partialName() {
        return new MemberFilter(Optional.empty(),
            Optional.of(MemberConstants.FIRST_NAME.substring(0, MemberConstants.FIRST_NAME.length() - 2)));
    }

    private MemberFilters() {
        super();
    }

}
