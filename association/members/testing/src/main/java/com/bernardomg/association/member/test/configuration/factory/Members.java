
package com.bernardomg.association.member.test.configuration.factory;

import com.bernardomg.association.member.domain.model.Member;
import com.bernardomg.association.member.domain.model.Member.Name;

public final class Members {

    public static final Member alternative() {
        final Name name;

        name = new Name(MemberConstants.ALTERNATIVE_FIRST_NAME, MemberConstants.ALTERNATIVE_LAST_NAME);
        return new Member(MemberConstants.ALTERNATIVE_NUMBER, name, true);
    }

    public static final Member forNumber(final long number) {
        final Name name;

        name = new Name("Name " + number, "Last name " + number);
        return new Member(number * 10, name, true);
    }

    public static final Member noRenew() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.NUMBER, name, false);
    }

    public static final Member valid() {
        final Name name;

        name = new Name(MemberConstants.FIRST_NAME, MemberConstants.LAST_NAME);
        return new Member(MemberConstants.NUMBER, name, true);
    }

}
