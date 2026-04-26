
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.Name;
import com.bernardomg.association.profile.test.configuration.factory.ProfileConstants;

public final class Members {

    public static final Member alternative() {
        final Name name;

        name = new Name(ProfileConstants.ALTERNATIVE_FIRST_NAME, ProfileConstants.ALTERNATIVE_LAST_NAME);
        return new Member(ProfileConstants.ALTERNATIVE_NUMBER, name, true);
    }

    public static final Member forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Member(number * 10, name, true);
    }

    public static final Member noRenew() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, false);
    }

    public static final Member valid() {
        final Name name;

        name = new Name(ProfileConstants.FIRST_NAME, ProfileConstants.LAST_NAME);
        return new Member(ProfileConstants.NUMBER, name, true);
    }

}
