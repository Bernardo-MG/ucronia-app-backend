
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.PublicMemberFilter;

public final class PublicMemberFilters {

    public static final PublicMemberFilter alternativeFirstName() {
        return new PublicMemberFilter(Optional.of(MemberConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final PublicMemberFilter empty() {
        return new PublicMemberFilter(Optional.empty());
    }

    public static final PublicMemberFilter firstName() {
        return new PublicMemberFilter(Optional.of(MemberConstants.FIRST_NAME));
    }

    public static final PublicMemberFilter fullName() {
        return new PublicMemberFilter(Optional.of(MemberConstants.FULL_NAME));
    }

    public static final PublicMemberFilter lastName() {
        return new PublicMemberFilter(Optional.of(MemberConstants.LAST_NAME));
    }

    public static final PublicMemberFilter partialName() {
        return new PublicMemberFilter(
            Optional.of(MemberConstants.FIRST_NAME.substring(0, MemberConstants.FIRST_NAME.length() - 2)));
    }

    private PublicMemberFilters() {
        super();
    }

}
