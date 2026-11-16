
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.PublicMemberFilter;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class PublicMemberFilters {

    public static final PublicMemberFilter active() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.empty());
    }

    public static final PublicMemberFilter all() {
        return new PublicMemberFilter(MemberStatus.ALL, Optional.empty());
    }

    public static final PublicMemberFilter alternativeFirstName() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final PublicMemberFilter firstName() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final PublicMemberFilter fullName() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final PublicMemberFilter inactive() {
        return new PublicMemberFilter(MemberStatus.INACTIVE, Optional.empty());
    }

    public static final PublicMemberFilter lastName() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final PublicMemberFilter nickname() {
        return new PublicMemberFilter(MemberStatus.ACTIVE, Optional.of(ProfileConstants.NICKNAME));
    }

    public static final PublicMemberFilter partialName() {
        return new PublicMemberFilter(MemberStatus.ACTIVE,
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private PublicMemberFilters() {
        super();
    }

}
