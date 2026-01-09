
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.profile.domain.model.ProfileName;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Members {

    public static final Member alternative() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ProfileConstants.ALTERNATIVE_NUMBER, name);
    }

    public static final Member forNumber(final long number) {
        final ProfileName name;

        name = new ProfileName("Profile " + number, "Last name " + number);
        return new Member(number * 10, name);
    }

    public static final Member valid() {
        final ProfileName name;

        name = new ProfileName(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name);
    }

}
