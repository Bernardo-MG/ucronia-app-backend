
package com.bernardomg.association.member.test.configuration.factory;

import java.util.Optional;

import com.bernardomg.association.member.domain.filter.MemberFilter;
import com.bernardomg.association.member.domain.model.MemberStatus;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class MemberFilters {

    public static final MemberFilter active() {
        return new MemberFilter(MemberStatus.ACTIVE, Optional.empty());
    }

    public static final MemberFilter all() {
        return new MemberFilter(MemberStatus.ALL, Optional.empty());
    }

    public static final MemberFilter alternativeFirstName() {
        return new MemberFilter(MemberStatus.INACTIVE, Optional.of(ProfileConstants.ALTERNATIVE_FIRST_NAME));
    }

    public static final MemberFilter firstName() {
        return new MemberFilter(MemberStatus.ALL, Optional.of(ProfileConstants.FIRST_NAME));
    }

    public static final MemberFilter fullName() {
        return new MemberFilter(MemberStatus.ALL, Optional.of(ProfileConstants.FULL_NAME));
    }

    public static final MemberFilter inactive() {
        return new MemberFilter(MemberStatus.INACTIVE, Optional.empty());
    }

    public static final MemberFilter lastName() {
        return new MemberFilter(MemberStatus.ALL, Optional.of(ProfileConstants.LAST_NAME));
    }

    public static final MemberFilter nickname() {
        return new MemberFilter(MemberStatus.ALL, Optional.of(ProfileConstants.NICKNAME));
    }

    public static final MemberFilter partialName() {
        return new MemberFilter(MemberStatus.ALL,
            Optional.of(ProfileConstants.FIRST_NAME.substring(0, ProfileConstants.FIRST_NAME.length() - 2)));
    }

    private MemberFilters() {
        super();
    }

}
