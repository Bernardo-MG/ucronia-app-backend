
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class PublicMemberFilters {

    public static final PublicMemberFilter alternativeFirstName() {
        return new PublicMemberFilter(Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final PublicMemberFilter empty() {
        return new PublicMemberFilter(Optional.empty());
    }

    public static final PublicMemberFilter firstName() {
        return new PublicMemberFilter(Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final PublicMemberFilter fullName() {
        return new PublicMemberFilter(Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final PublicMemberFilter lastName() {
        return new PublicMemberFilter(Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final PublicMemberFilter partialName() {
        return new PublicMemberFilter(
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private PublicMemberFilters() {
        super();
    }

}
